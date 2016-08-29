package com.pokemonify.pokemonify.pokemondatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gaurav on 2/8/16.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static DbHelper mDbHelper = null;
    private static final int DATABASE_VERSION = 10;
    private Context mContext;

    // Database Name
    private static final String DATABASE_NAME = "Test";
    private static final String TABLE_MYCARDS = "myCards";
    private static final String TABLE_MYPOKEMON = "myPokemon";

    // Common column names
    private static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_HP = "hp";
    public static final String KEY_BITMAP_PATH = "image";
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
            KEY_BITMAP_PATH + " VARCHAR," +
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
            KEY_BITMAP_PATH + " VARCHAR," +
            KEY_IMAGE_PATH + " VARCHAR," +
            KEY_TYPE + " VARCHAR," +
            KEY_DESC + " VARCHAR," +
            KEY_WEIGHT + " INTEGER," +
            KEY_HEIGHT + " INTEGER," +
            KEY_LEVEL + " INTEGER " +
            ")";

    private PokemonDto myCurrentPokemon = null;
    private PokemonDto mPokemonDto = null;
    private List<PokemonDto> myCardsList = new ArrayList<PokemonDto>();
    private Runnable setMyCardsListRunnable;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext=context;
        mDbHelper = this;
    }

    public static DbHelper getInstance() {
        return mDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_MYCARD);
        db.execSQL(CREATE_TABLE_MYPOKEMON);
    }

    private boolean execInThread(Runnable r) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(r);
        service.shutdown();
        Log.d("TAG","end time"+System.currentTimeMillis()+"");
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYCARDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYPOKEMON);
        // create new tables
        onCreate(db);
    }

    public boolean saveMyCard(PokemonDto cardsDto) {
        myCardsList.add(cardsDto);
        mPokemonDto=new PokemonDto();
        mPokemonDto = cardsDto;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = DbHelper.this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(KEY_ID, mPokemonDto.getId());
                values.put(KEY_NAME, mPokemonDto.getName());
                values.put(KEY_HP, mPokemonDto.getHp());
                values.put(KEY_IMAGE_PATH, mPokemonDto.getImagePath());
                values.put(KEY_BITMAP_PATH,mPokemonDto.getBitmapPath());
                values.put(KEY_TYPE, mPokemonDto.getType());
                values.put(KEY_DESC, mPokemonDto.getDesc());
                values.put(KEY_WEIGHT, mPokemonDto.getWeight());
                values.put(KEY_HEIGHT, mPokemonDto.getHeight());
                values.put(KEY_LEVEL, mPokemonDto.getLevel());
                db.insert(TABLE_MYCARDS, null, values);

                closeDB();
            }
        };
        return execInThread(runnable);
    }

    public boolean updateMyCard(PokemonDto cardsDto) {
        PokemonDto temp = null;
        for(PokemonDto dto:myCardsList) {
            if(dto.getId()==cardsDto.getId()) {
                temp=dto;
                break;
            }
        }
        if(temp!=null) {
            myCardsList.remove(temp);
        }
        myCardsList.add(cardsDto);
        mPokemonDto=new PokemonDto();
        mPokemonDto = cardsDto;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = DbHelper.this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(KEY_ID, mPokemonDto.getId());
                values.put(KEY_NAME, mPokemonDto.getName());
                values.put(KEY_HP, mPokemonDto.getHp());
                values.put(KEY_IMAGE_PATH, mPokemonDto.getImagePath());
                values.put(KEY_BITMAP_PATH,mPokemonDto.getBitmapPath());
                values.put(KEY_TYPE, mPokemonDto.getType());
                values.put(KEY_DESC, mPokemonDto.getDesc());
                values.put(KEY_WEIGHT, mPokemonDto.getWeight());
                values.put(KEY_HEIGHT, mPokemonDto.getHeight());
                values.put(KEY_LEVEL, mPokemonDto.getLevel());
                db.update(TABLE_MYCARDS,values,"id = ?",new String[]{mPokemonDto.getId()+""});

                closeDB();
            }
        };
        return execInThread(runnable);
    }

    public boolean saveMyPokemon(final PokemonDto pokemonDto) {

        myCurrentPokemon = pokemonDto;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put(KEY_ID, pokemonDto.getId());
                values.put(KEY_NAME, pokemonDto.getName());
                values.put(KEY_HP, pokemonDto.getHp());
                values.put(KEY_IMAGE_PATH, pokemonDto.getImagePath());
                values.put(KEY_BITMAP_PATH, pokemonDto.getBitmapPath());
                values.put(KEY_TYPE, pokemonDto.getType());
                values.put(KEY_DESC, pokemonDto.getDesc());
                values.put(KEY_WEIGHT, pokemonDto.getWeight());
                values.put(KEY_HEIGHT, pokemonDto.getHeight());
                values.put(KEY_LEVEL, pokemonDto.getLevel());

                SQLiteDatabase db = DbHelper.this.getWritableDatabase();
                db.delete(TABLE_MYPOKEMON, null, null);
                db.insert(TABLE_MYPOKEMON, null, values);

                closeDB();

            }
        };
        return execInThread(runnable);
    }

    public List<PokemonDto> getAllMyCards() {
        if (myCardsList == null) {
            return setMyCardsList();
        } else {
            return myCardsList;
        }
    }

    public List<PokemonDto> setMyCardsList() {
         setMyCardsListRunnable = new Runnable() {
            @Override
            public void run() {
                myCardsList.clear();
                String selectQuery = "SELECT  * FROM " + TABLE_MYCARDS;

                SQLiteDatabase db = DbHelper.this.getReadableDatabase();
                Cursor c = db.rawQuery(selectQuery, null);
                // looping through all rows and adding to list
                if (c.moveToFirst()) {
                    do {
                        PokemonDto dto = new PokemonDto();
                        dto.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                        dto.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                        dto.setHp(c.getInt(c.getColumnIndex(KEY_HP)));
                        dto.setBitmapPath(c.getString(c.getColumnIndex(KEY_BITMAP_PATH)));
                        dto.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
                        dto.setDesc(c.getString(c.getColumnIndex(KEY_DESC)));
                        dto.setWeight(c.getInt(c.getColumnIndex(KEY_WEIGHT)));
                        dto.setHeight(c.getInt(c.getColumnIndex(KEY_HEIGHT)));
                        dto.setLevel(c.getInt(c.getColumnIndex(KEY_LEVEL)));
                        myCardsList.add(dto);
                    } while (c.moveToNext());
                }
                closeDB();
            }
        };
        execInThread(setMyCardsListRunnable);
        return myCardsList;
    }

    public PokemonDto getMyPokemon() {

        if (myCurrentPokemon == null) {
            return setMyCurrentPokemon();
        } else {
            return myCurrentPokemon;
        }
    }

    public PokemonDto setMyCurrentPokemon() {
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MYPOKEMON, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            myCurrentPokemon = new PokemonDto();
            myCurrentPokemon.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            myCurrentPokemon.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            myCurrentPokemon.setHp(c.getInt(c.getColumnIndex(KEY_HP)));
            myCurrentPokemon.setImagePath(c.getString(c.getColumnIndex(KEY_IMAGE_PATH)));
            myCurrentPokemon.setBitmapPath(c.getString(c.getColumnIndex(KEY_BITMAP_PATH)));
            myCurrentPokemon.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
            myCurrentPokemon.setDesc(c.getString(c.getColumnIndex(KEY_DESC)));
            myCurrentPokemon.setWeight(c.getInt(c.getColumnIndex(KEY_WEIGHT)));
            myCurrentPokemon.setHeight(c.getInt(c.getColumnIndex(KEY_HEIGHT)));
            myCurrentPokemon.setLevel(c.getInt(c.getColumnIndex(KEY_LEVEL)));
        }
        closeDB();
        return myCurrentPokemon;
    }

    public int deleteCard(long id) {
        Log.d("TAG","deleting"+ id + "");
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            File file=new File(mContext.getFilesDir()+File.separator+id+".png");
            if(file.exists()) {
                file.delete();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        int temp = db.delete(TABLE_MYCARDS, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        closeDB();
        if (temp != 0) {
            if(execInThread(setMyCardsListRunnable)){
                return temp;
            }
        }
        return 0;
    }

    public void closeDB() {
       /* SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();*/
    }
}
