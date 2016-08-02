package com.pokemonify.pokemonify.pokemondatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 2/8/16.
 */
public class DbHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "Test";
    private static final String TABLE_MYCARDS = "myCards";

    // Common column names
    private static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_HP = "hp";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DESC = "desc";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_LEVEL = "level";

    private static final String CREATE_TABLE_MYCARD = "CREATE TABLE "
            + TABLE_MYCARDS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY,"+
            KEY_NAME+ " VARCHAR," +
            KEY_HP +" VARCHAR," +
            KEY_IMAGE + " BLOB," +
            KEY_TYPE + " VARCHAR," +
            KEY_DESC + " VARCHAR," +
            KEY_WEIGHT +" INTEGER," +
            KEY_HEIGHT +" INTEGER," +
            KEY_LEVEL +" INTEGER " +
            ")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_MYCARD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYCARDS);
        // create new tables
        onCreate(db);
    }

    public void saveMyCard(MyCardsDto cardsDto) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,cardsDto.getId());
        values.put(KEY_NAME,cardsDto.getName());
        values.put(KEY_HP,cardsDto.getHp());
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        cardsDto.getImage().compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        values.put(KEY_IMAGE,byteArrayOutputStream.toByteArray());
        values.put(KEY_TYPE,cardsDto.getType());
        values.put(KEY_DESC,cardsDto.getDesc());
        values.put(KEY_WEIGHT,cardsDto.getWeight());
        values.put(KEY_HEIGHT,cardsDto.getHeight());
        values.put(KEY_LEVEL,cardsDto.getLevel());
        db.insert(TABLE_MYCARDS, null, values);
    }

    public List<MyCardsDto> getAllMyCards() {
        List<MyCardsDto> cardsDtos = new ArrayList<MyCardsDto>();
        String selectQuery = "SELECT  * FROM " + TABLE_MYCARDS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        MyCardsDto dto=new MyCardsDto();
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                dto.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                dto.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                dto.setHp(c.getInt(c.getColumnIndex(KEY_HP)));
                byte[] a=c.getBlob(c.getColumnIndex(KEY_IMAGE));
                dto.setImage(BitmapFactory.decodeByteArray(a,0,a.length));
                dto.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
                dto.setDesc(c.getString(c.getColumnIndex(KEY_DESC)));
                dto.setWeight(c.getInt(c.getColumnIndex(KEY_WEIGHT)));
                dto.setHeight(c.getInt(c.getColumnIndex(KEY_HEIGHT)));
                dto.setLevel(c.getInt(c.getColumnIndex(KEY_LEVEL)));
                cardsDtos.add(dto);
            } while (c.moveToNext());
        }

        return cardsDtos;
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
