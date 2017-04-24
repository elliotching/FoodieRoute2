List of SharedPreference:
1."FCM"
1.1."notification_number"
1.2.


2. "FoodieRoute"
2.1."FR_deviceUUID" - "empty"
2.2 "FR_theme" - "Light" / "Dark"
2.3."FR_first_time_launch" - defaultBoolean:true
2.4."FR_token" - "empty"
2.5."FR_username" - "empty"
2.6."FR_firebase_IID" - "empty"
2.7."FR_device" - "empty"



SharedPreferencces:
READ:
SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
String deviceUUID = pref.getString("FR_deviceUUID", "empty");
SAVE:
SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
pref.edit().putString("FR_deviceUUID", deviceUUID);
pref.edit().commit();



Button must put style:
Coloured or non-coloured
@style/style_for_coloured_button_and_text_size


For testing only:
1. Username use test, for splash screen and FirebaseInstanceIDServices
2.


AppCompatActivity <<<--- (AppCompatActivity) Context    (OK)
AppCompatActivity <<<--- Context                        (NOT OK)

Context <<<--- AppCompatActivity                        (OK)



// example usage of WebView calibrate map
// JAVA
        WebView wView = new WebView(context);
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 235, metrics),
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 235, metrics));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 1);
        rl.addView(wView, layoutParams);
        wView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wView.getSettings().setLoadWithOverviewMode(false);
        wView.getSettings().setUseWideViewPort(true);
        wView.loadUrl("file:///android_asset/cal.gif");
// XML
        <RelativeLayout
            android:id="@+id/rl"
            android:layout_width="235dp"
            android:layout_height="235dp"
            android:layout_gravity="center_horizontal"
            android:gravity="center">
        </RelativeLayout>





IMAGE LOADER
IMAGE LOADER
IMAGE LOADER
IMAGE LOADER
IMAGE LOADER

Ion.with(context)
.load(imgURL) // String
.withBitmap()
.placeholder(R.mipmap.loading)
.intoImageView(holder.imageView);





set IMAGE VIEW from PATH
set IMAGE VIEW from PATH
set IMAGE VIEW from PATH
set IMAGE VIEW from PATH
set IMAGE VIEW from PATH

File imgFile = new  File("/sdcard/Images/test_image.jpg");
if(imgFile.exists()){
    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
    ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
    myImage.setImageBitmap(myBitmap);
}




Toolbar
Toolbar
Toolbar
Toolbar
Toolbar
Toolbar
Toolbar

No need theme liao use toolbar style as:

<android.support.v7.widget.Toolbar
    android:id="@+id/toolbar"
    style="@style/toolbar_style"
    app:title="@string/s_title_toolbar_activity_food_detail" />




































NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
NOT IMPORTANT
/*
*
    private class MyEntity implements HttpEntity {

        // Size of the byte-array buffer used in I/O streams.
        private final int BUFFER_SIZE = 4096;
        private String json;

        public MyEntity(String jsonstring) {
            json = jsonstring;
        }

        @Override
        public boolean isRepeatable() {
            return false;
        }

        @Override
        public boolean isChunked() {
            return false;
        }

        @Override
        public boolean isStreaming() {
            return false;
        }

        @Override
        public long getContentLength() {
            return -1;
        }

        @Override
        public Header getContentEncoding() {
            return null;
        }

        @Override
        public Header getContentType() {
            return null;
        }

        @Override
        public void consumeContent() throws IOException, UnsupportedOperationException {
        }

        @Override
        public InputStream getContent() throws IOException, UnsupportedOperationException {
            return null;
        }

        @Override
        public void writeTo(final OutputStream out) throws IOException {
            if (out == null) {
                throw new IllegalStateException("Output stream cannot be null.");
            }

            // Record the time when uploading started.
            long now = System.currentTimeMillis();

            OutputStream os = out;

            int[] chararray = new int[json.length()];
            for (int count = 0; count < json.length(); count++)
                chararray[count] = json.charAt(count);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            for (int count = 0; count < json.length(); count++)
                bos.write(chararray[count]);
            bos.close();
            byte[] arr = bos.toByteArray();

            os.write(arr); // or: bos.writeTo(os);
            os.close();

            System.out.println("bos.toString() = " + new String(bos.toByteArray()));
            os.flush();
            AsyncHttpClient.silentCloseOutputStream(os);
        }
    }

* */