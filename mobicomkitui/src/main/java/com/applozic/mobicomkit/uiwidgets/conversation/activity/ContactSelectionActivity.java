package com.applozic.mobicomkit.uiwidgets.conversation.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AlphabetIndexer;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.api.people.ChannelCreate;
import com.applozic.mobicomkit.channel.service.ChannelService;
import com.applozic.mobicomkit.contact.AppContactService;
import com.applozic.mobicomkit.contact.BaseContactService;
import com.applozic.mobicomkit.uiwidgets.R;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicommons.commons.core.utils.Utils;
import com.applozic.mobicommons.commons.image.ImageLoader;
import com.applozic.mobicommons.people.channel.Channel;
import com.applozic.mobicommons.people.contact.Contact;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 6/2/16.
 */
public class ContactSelectionActivity extends ActionBarActivity {
    public static final String CHANNEL = "CHANNEL_NAME";
    public static final String CHANNEL_OBJECT = "CHANNEL";
    public static final String CHECK_BOX = "CHECK_BOX";
    ListView mainListView;
    Channel channel;
    private String name;
    private ContactsAdapter mAdapter;
    private ImageLoader mImageLoader;
    private BaseContactService contactService;
    private List<Contact> contactList;
    private ActionBar mActionBar;
    boolean disableCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_select_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        if (getIntent().getExtras() != null) {
            channel = (Channel) getIntent().getSerializableExtra(CHANNEL_OBJECT);
            disableCheckBox = getIntent().getBooleanExtra(CHECK_BOX, false);
            mActionBar.setTitle(R.string.channel_member_title);
        } else {
            mActionBar.setTitle(R.string.channel_members_title);
        }

        mainListView = (ListView) findViewById(R.id.mainList);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item,
                                    int position, long id) {
                Contact contact = contactList.get(position);
                if (disableCheckBox) {
                    Intent intent = new Intent();
                    if (!TextUtils.isEmpty(contact.getUserId())) {
                        intent.putExtra(ChannelInfoActivity.USERID, contact.getUserId());
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }
                contact.toggleChecked();
                ContactViewHolder viewHolder = (ContactViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked(contact.isChecked());
            }
        });

        mImageLoader = new ImageLoader(this, getListPreferredItemHeight()) {
            @Override
            protected Bitmap processBitmap(Object data) {
                return contactService.downloadContactImage(getApplicationContext(), (Contact) data);
            }
        };
        mImageLoader.setLoadingImage(R.drawable.applozic_ic_contact_picture_holo_light);
        mImageLoader.addImageCache(this.getSupportFragmentManager(), 0.1f);

        contactService = new AppContactService(this);
        contactList = contactService.getAll();
        mAdapter = new ContactsAdapter(this);
        mainListView.setAdapter(mAdapter);
        mainListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                // Pause image loader to ensure smoother scrolling when flinging
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    Utils.toggleSoftKeyBoard(ContactSelectionActivity.this, true);
                    mImageLoader.setPauseWork(true);
                } else {
                    mImageLoader.setPauseWork(false);
                }
            }
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }

        });

    }

    private int getListPreferredItemHeight() {
        final TypedValue typedValue = new TypedValue();
        this.getTheme().resolveAttribute(
                android.R.attr.listPreferredItemHeight, typedValue, true);
        final DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return (int) typedValue.getDimension(metrics);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_create_menu, menu);
        menu.removeItem(R.id.Next);
        if (disableCheckBox) {
            menu.removeItem(R.id.Done);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Done) {
            if (mAdapter.getResult().size() == 0) {
                Toast.makeText(this, R.string.select_at_least, Toast.LENGTH_SHORT).show();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        name = getIntent().getStringExtra(CHANNEL);
                        List<String> channelMemberNames = null;
                        if (!TextUtils.isEmpty(name) && mAdapter.getResult().size() > 0) {
                            channelMemberNames = mAdapter.getResult();
                            ChannelCreate channelCreate = new ChannelCreate(name, channelMemberNames);
                            Channel channel = ChannelService.getInstance(ContactSelectionActivity.this).createChannel(channelCreate);
                            if (channel != null) {
                                Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
                                intent.putExtra(ConversationUIService.GROUP_ID, channel.getKey());
                                intent.putExtra(ConversationUIService.GROUP_NAME, channel.getName());
                                startActivity(intent);
                            }
                        }
                    }
                }).start();
                ChannelCreateActivity.channelActivity.finish();
                finish();
            }
            return true;
        }
        return false;

    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }
    @Override
    public void onPause() {
        super.onPause();
        mImageLoader.setPauseWork(false);
    }


    private class ContactsAdapter extends BaseAdapter {
        Context context;

        CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                ((Contact) buttonView.getTag()).setChecked(isChecked);
            }
        };

        private LayoutInflater mInflater; // Stores the layout inflater
        private AlphabetIndexer mAlphabetIndexer; // Stores the AlphabetIndexer instance
        private TextAppearanceSpan highlightTextSpan; // Stores the highlight text appearance style

        public ContactsAdapter(Context context) {
            this.context = context;
            mInflater = LayoutInflater.from(context);
            final String alphabet = context.getString(R.string.alphabet);

            highlightTextSpan = new TextAppearanceSpan(ContactSelectionActivity.this, R.style.searchTextHiglight);
        }

        /**
         * Overrides newView() to inflate the list item views.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Inflates the list item layout.
            CheckBox checkBox;
            TextView text2;
            TextView text1;
            Contact contact = getContact(position);
            CircleImageView circleImageView;
            if (convertView == null) {
                convertView =
                        mInflater.inflate(R.layout.contact_select_list_item, parent, false);

                text1 = (TextView) convertView.findViewById(R.id.userId);
                text2 = (TextView) convertView.findViewById(R.id.displayName);
                checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
                checkBox.setVisibility(View.VISIBLE);
                circleImageView = (CircleImageView) convertView.findViewById(R.id.contactImage);
                convertView.setTag(new ContactViewHolder(text1, text2, checkBox, circleImageView));
            } else {
                ContactViewHolder viewHolder = (ContactViewHolder) convertView
                        .getTag();
                checkBox = viewHolder.getCheckBox();
                text1 = viewHolder.getTextView1();
                text2 = viewHolder.getTextView2();
                circleImageView = viewHolder.getCircleImageView();
            }
            if (disableCheckBox) {
                checkBox.setVisibility(View.GONE);
            }

            if (contact.isDrawableResources()) {
                int drawableResourceId = context.getResources().getIdentifier(contact.getrDrawableName(), "drawable", context.getPackageName());
                circleImageView.setImageResource(drawableResourceId);
            } else {
                mImageLoader.loadImage(contact, circleImageView);
            }

            checkBox.setTag(contact);
            checkBox.setChecked(contact.isChecked());
            checkBox.setOnCheckedChangeListener(myCheckChangList);
            text1.setText(contact.getUserId());
            text2.setText(contact.getDisplayName());
            return convertView;
        }

        List<Contact> getContacts() {
            List<Contact> selectedContactList = new ArrayList<>();
            for (Contact contact : contactList) {
                if (contact.isChecked()) {
                    selectedContactList.add(contact);

                }
            }
            return selectedContactList;
        }

        List<String> getResult() {
            List<String> membersList = new ArrayList<>();
            for (Contact contact : getContacts()) {
                if (contact.isChecked()) {
                    membersList.add(contact.getContactIds());
                }
            }
            return membersList;
        }

        /**
         * An override of getCount that simplifies accessing the Cursor. If the Cursor is null,
         * getCount returns zero. As a result, no test for Cursor == null is needed.
         */
        @Override
        public int getCount() {
            return contactList.size();
        }

        @Override
        public Object getItem(int position) {
            return contactList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        Contact getContact(int position) {
            return ((Contact) getItem(position));
        }

    }

    private class ContactViewHolder {
        private CheckBox checkBox;
        private TextView textView1;
        private CircleImageView circleImageView;
        private TextView textView2;


        public ContactViewHolder() {
        }

        public ContactViewHolder(TextView textView1, TextView textView2, CheckBox checkBox, CircleImageView circleImageView) {
            this.checkBox = checkBox;
            this.textView1 = textView1;
            this.textView2 = textView2;
            this.circleImageView = circleImageView;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        public CircleImageView getCircleImageView() {
            return circleImageView;
        }

        public void setCircleImageView(CircleImageView circleImageView) {
            this.circleImageView = circleImageView;
        }

        public TextView getTextView1() {
            return textView1;
        }

        public void setTextView1(TextView textView2) {
            this.textView1 = textView2;
        }

        public TextView getTextView2() {
            return textView2;
        }

        public void setTextView2(TextView textView2) {
            this.textView2 = textView2;
        }

    }

}
