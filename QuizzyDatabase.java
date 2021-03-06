import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
//new file
public class QuizzyDatabase extends SQLiteOpenHelper {

    public QuizzyDatabase(Context context, String name, CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String dataTable="create table QUIZDATA( "
                + "id"
                + " integer primary key autoincrement,"
                + "CATAGORY text,CATAGORY_NAME text,CATAGORY_IMG_PATH text,CHAP_ID text not null UNIQUE,CHAP_NAME text, CHAP_IMG_PATH text); ";

       String scoreTable="create table SCOREDATA("
                + "id"
                + " integer primary key autoincrement,"
                + "CATAGORY text,CATAGORY_NAME text,CHAP_ID text not null UNIQUE,CHAP_NAME text,GLOBAL_RANK text,TOTAL_SCORE text,CORRECT_QUES text, COUNT text); ";


        String friendsTable ="create table FRIENDSDATA("
                + "id"
                + " integer primary key autoincrement,"
                + "Uid text, Name text,Pic text); ";


        db.execSQL(dataTable);
        db.execSQL(scoreTable);
        db.execSQL(friendsTable);

    }

    public void insertUpdatesQUIZDATA(String CATAGORY,String CATAGORY_NAME,String CATAGORY_IMG_PATH,String CHAP_ID, String CHAP_NAME, String CHAP_IMG_PATH) {
        ContentValues cv = new ContentValues();
        cv.put("CATAGORY", CATAGORY);
        cv.put("CATAGORY_NAME", CATAGORY_NAME);
        cv.put("CATAGORY_IMG_PATH", CATAGORY_IMG_PATH);
        cv.put("CHAP_ID", CHAP_ID);
        cv.put("CHAP_NAME", CHAP_NAME);
        cv.put("CHAP_IMG_PATH", CHAP_IMG_PATH);

        getWritableDatabase().insert("QUIZDATA", null, cv);
    }

    public void insertUpdatesSCOREDATA(String CATAGORY,String CATAGORY_NAME,String CHAP_ID,String CHAP_NAME,String GLOBAL_RANK,String TOTAL_SCORE, String CORRECT_QUES, String COUNT) {
        ContentValues cv = new ContentValues();
        cv.put("CATAGORY", CATAGORY);
        cv.put("CATAGORY_NAME", CATAGORY_NAME);
        cv.put("CHAP_ID", CHAP_ID);
        cv.put("CHAP_NAME", CHAP_NAME);
        cv.put("GLOBAL_RANK", GLOBAL_RANK);
        cv.put("TOTAL_SCORE", TOTAL_SCORE);
        cv.put("CORRECT_QUES", CORRECT_QUES);
        cv.put("COUNT", COUNT);

        getWritableDatabase().insert("SCOREDATA", null, cv);
    }

    public void insertFriends(String Id, String Name, String Pic){

        ContentValues cv = new ContentValues();
        cv.put("Uid", Id);
        cv.put("Name", Name);
        cv.put("Pic", Pic);

        getWritableDatabase().insert("FRIENDSDATA", null, cv);

    }


    public Cursor getFriendsList() {

        Cursor c = getWritableDatabase().rawQuery("select * from FRIENDSDATA", null);

        return c;
    }


    public Cursor getUpdatesQUIZDATA() {

        Cursor c = getWritableDatabase().rawQuery("select * from QUIZDATA", null);

        return c;
    }

    public Cursor getUpdatesSCOREDATA() {

        Cursor c = getWritableDatabase().rawQuery("select * from SCOREDATA", null);

        return c;
    }

    public boolean updateTotalScore(String CATAGORY,String CHAP_ID, String TOTAL_SCORE) {
        Cursor c = getDataSCOREDATA(CATAGORY, CHAP_ID);
        c.moveToFirst();
        String rowId = c.getString(0);
        c.close();
        ContentValues args = new ContentValues();
        args.put("TOTAL_SCORE", TOTAL_SCORE);
        return getWritableDatabase().update("SCOREDATA", args, "id='"+rowId+"'", null) > 0;
    }

    public boolean updateGlobalRank(String CATAGORY,String CHAP_ID, String GLOBAL_RANK) {
        Cursor c = getDataSCOREDATA(CATAGORY, CHAP_ID);
        c.moveToFirst();
        String rowId = c.getString(0);
        c.close();
        ContentValues args = new ContentValues();
        args.put("GLOBAL_RANK", GLOBAL_RANK);
        return getWritableDatabase().update("SCOREDATA", args, "id='"+rowId+"'", null) > 0;
    }


    public boolean updateFullScore(String CATAGORY,String CHAP_ID, String GLOBAL_RANK,String TOTAL_SCORE,String CORRECT_QUES,String COUNT) {
        Cursor c = getDataSCOREDATA(CATAGORY, CHAP_ID);
        c.moveToFirst();
        String rowId = c.getString(0);
        c.close();
        ContentValues args = new ContentValues();
        args.put("GLOBAL_RANK", GLOBAL_RANK);
        args.put("TOTAL_SCORE", TOTAL_SCORE);
        args.put("CORRECT_QUES", CORRECT_QUES);
        args.put("COUNT", COUNT);
        return getWritableDatabase().update("SCOREDATA", args, "id='"+rowId+"'", null) > 0;
    }

    public Cursor getDataSCOREDATA(String CATAGORY,String CHAP_ID) {
        Cursor c = getWritableDatabase().rawQuery("select * from SCOREDATA where CATAGORY='"+CATAGORY+"' AND CHAP_ID ='"+CHAP_ID+"'", null);
        return c;
    }

    public String getGlobalRank(String CATAGORY,String CHAP_ID) {
        Cursor c = getDataSCOREDATA(CATAGORY, CHAP_ID);
        c.moveToFirst();
        String rank = c.getString(c.getColumnIndex("GLOBAL_RANK"));
        c.close();
        return rank;
    }

    public String getTotalScore(String CATAGORY,String CHAP_ID) {
        Cursor c = getDataSCOREDATA(CATAGORY, CHAP_ID);
        c.moveToFirst();
        String rank = c.getString(c.getColumnIndex("TOTAL_SCORE"));
        c.close();
        return rank;
    }

    public String getTotalCount(String CATAGORY,String CHAP_ID) {
        Cursor c = getDataSCOREDATA(CATAGORY, CHAP_ID);
        c.moveToFirst();
        String rank = c.getString(c.getColumnIndex("COUNT"));
        c.close();
        return rank;
    }

    public boolean updateTotalCount(String CATAGORY,String CHAP_ID, String COUNT) {
        Cursor c = getDataSCOREDATA(CATAGORY, CHAP_ID);
        c.moveToFirst();
        String rowId = c.getString(0);
        c.close();
        ContentValues args = new ContentValues();
        args.put("COUNT", COUNT);
        return getWritableDatabase().update("SCOREDATA", args, "id='"+rowId+"'", null) > 0;
    }

    public String getTotalCorrect(String CATAGORY,String CHAP_ID) {
        Cursor c = getDataSCOREDATA(CATAGORY, CHAP_ID);
        c.moveToFirst();
        String rank = c.getString(c.getColumnIndex("CORRECT_QUES"));
        c.close();
        return rank;
    }

    public boolean updateTotalCorrect(String CATAGORY,String CHAP_ID, String CORRECT_QUES) {
        Cursor c = getDataSCOREDATA(CATAGORY, CHAP_ID);
        c.moveToFirst();
        String rowId = c.getString(0);
        c.close();
        ContentValues args = new ContentValues();
        args.put("CORRECT_QUES", CORRECT_QUES);
        return getWritableDatabase().update("SCOREDATA", args, "id='"+rowId+"'", null) > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {


    }

    public Cursor getListOfChaptersInCatagory(String CATAGORY_VAL)
    {
        return getWritableDatabase().query("QUIZDATA",new String[]{"CHAP_ID","CHAP_NAME"},"CATAGORY "+"=?",new String[]{CATAGORY_VAL},null,null,null,null);
    }

}
