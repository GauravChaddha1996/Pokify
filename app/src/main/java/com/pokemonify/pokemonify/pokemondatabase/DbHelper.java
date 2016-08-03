package com.pokemonify.pokemonify.pokemondatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 2/8/16.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "Test";
    private static final String TABLE_MYCARDS = "myCards";
    private static final String TABLE_MYPOKEMON = "myPokemon";

    // Common column names
    private static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_HP = "hp";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_IMAGE_PATH = "imagePath";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DESC = "desc";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_LEVEL = "level";

    private static final String CREATE_TABLE_MYCARD = "CREATE TABLE "
            + TABLE_MYCARDS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_NAME + " VARCHAR," +
            KEY_HP + " VARCHAR," +
            KEY_IMAGE + " BLOB," +
            KEY_IMAGE_PATH + " VARCHAR," +
            KEY_TYPE + " VARCHAR," +
            KEY_DESC + " VARCHAR," +
            KEY_WEIGHT + " INTEGER," +
            KEY_HEIGHT + " INTEGER," +
            KEY_LEVEL + " INTEGER " +
            ")";
    private static final String CREATE_TABLE_MYPOKEMON = "CREATE TABLE "
            + TABLE_MYPOKEMON + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_NAME + " VARCHAR," +
            KEY_HP + " VARCHAR," +
            KEY_IMAGE + " BLOB," +
            KEY_IMAGE_PATH + " VARCHAR," +
            KEY_TYPE + " VARCHAR," +
            KEY_DESC + " VARCHAR," +
            KEY_WEIGHT + " INTEGER," +
            KEY_HEIGHT + " INTEGER," +
            KEY_LEVEL + " INTEGER " +
            ")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_MYCARD);
        db.execSQL(CREATE_TABLE_MYPOKEMON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYCARDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYPOKEMON);
        // create new tables
        onCreate(db);
    }

    public void saveMyCard(PokemonDto cardsDto) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, cardsDto.getId());
        values.put(KEY_NAME, cardsDto.getName());
        values.put(KEY_HP, cardsDto.getHp());
        values.put(KEY_IMAGE_PATH, cardsDto.getImagePath());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        cardsDto.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        values.put(KEY_IMAGE, byteArrayOutputStream.toByteArray());
        values.put(KEY_TYPE, cardsDto.getType());
        values.put(KEY_DESC, cardsDto.getDesc());
        values.put(KEY_WEIGHT, cardsDto.getWeight());
        values.put(KEY_HEIGHT, cardsDto.getHeight());
        values.put(KEY_LEVEL, cardsDto.getLevel());
        db.insert(TABLE_MYCARDS, null, values);

        closeDB();
    }

    public void saveMyPokemon(PokemonDto pokemonDto) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, pokemonDto.getId());
        values.put(KEY_NAME, pokemonDto.getName());
        values.put(KEY_HP, pokemonDto.getHp());
        values.put(KEY_IMAGE_PATH, pokemonDto.getImagePath());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if(pokemonDto.getImagePath().equals("-1")) {
            pokemonDto.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        }
        values.put(KEY_IMAGE, byteArrayOutputStream.toByteArray());
        values.put(KEY_TYPE, pokemonDto.getType());
        values.put(KEY_DESC, pokemonDto.getDesc());
        values.put(KEY_WEIGHT, pokemonDto.getWeight());
        values.put(KEY_HEIGHT, pokemonDto.getHeight());
        values.put(KEY_LEVEL, pokemonDto.getLevel());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MYPOKEMON,null,null);
        db.insert(TABLE_MYPOKEMON, null, values);

        closeDB();
    }

    public List<PokemonDto> getAllMyCards() {
        List<PokemonDto> cardsDtos = new ArrayList<PokemonDto>();
        String selectQuery = "SELECT  * FROM " + TABLE_MYCARDS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                PokemonDto dto = new PokemonDto();
                dto.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                dto.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                dto.setHp(c.getInt(c.getColumnIndex(KEY_HP)));
                byte[] a = c.getBlob(c.getColumnIndex(KEY_IMAGE));
                dto.setImagePath(c.getString(c.getColumnIndex(KEY_IMAGE_PATH)));
                if(dto.getImagePath().equals("-1")){
                    dto.setBitmap(BitmapFactory.decodeByteArray(a, 0, a.length));
                }else{
                    dto.setBitmap(null);
                }
                dto.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
                dto.setDesc(c.getString(c.getColumnIndex(KEY_DESC)));
                dto.setWeight(c.getInt(c.getColumnIndex(KEY_WEIGHT)));
                dto.setHeight(c.getInt(c.getColumnIndex(KEY_HEIGHT)));
                dto.setLevel(c.getInt(c.getColumnIndex(KEY_LEVEL)));
                Log.d("obj name is",dto.getName());
                cardsDtos.add(dto);
            } while (c.moveToNext());
        }
        closeDB();
        return cardsDtos;
    }

    public PokemonDto getMyPokemon() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_MYPOKEMON, null);
        PokemonDto dto = null;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
                dto=new PokemonDto();
                dto.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                dto.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                dto.setHp(c.getInt(c.getColumnIndex(KEY_HP)));
                dto.setImagePath(c.getString(c.getColumnIndex(KEY_IMAGE_PATH)));
                if(dto.getImagePath().equals("-1")) {
                    byte[] a = c.getBlob(c.getColumnIndex(KEY_IMAGE));
                    dto.setBitmap(BitmapFactory.decodeByteArray(a, 0, a.length));
                }else{
                    dto.setBitmap(null);
                }
                dto.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
                dto.setDesc(c.getString(c.getColumnIndex(KEY_DESC)));
                dto.setWeight(c.getInt(c.getColumnIndex(KEY_WEIGHT)));
                dto.setHeight(c.getInt(c.getColumnIndex(KEY_HEIGHT)));
                dto.setLevel(c.getInt(c.getColumnIndex(KEY_LEVEL)));
        }
        closeDB();
        return dto;
    }

    public int deleteCard(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        int temp=db.delete(TABLE_MYCARDS,KEY_NAME+" = ?",new String[]{name});
        closeDB();
        return temp;
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
