package fr.maximelucquin.falconexperience.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.ItemPutType;
import fr.maximelucquin.falconexperience.data.ItemType;

public class ItemManager {

    private static final String TABLE_NAME = "item";
    public static final String KEY_ID_ITEM="id_item";
    public static final String KEY_TYPE_ITEM ="type_animal";
    public static final String KEY_PUT_TYPE_ITEM ="put_type_animal";
    public static final String CREATE_TABLE_ITEM = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_ITEM+" VARCHAR(30) primary key," +
            " "+KEY_TYPE_ITEM+" VARCHAR(30)" +
            " "+KEY_PUT_TYPE_ITEM+" VARCHAR(30)" +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public ItemManager(Context context)
    {
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open()
    {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addItem(Item item) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE_ITEM, item.getType().name());
        values.put(KEY_PUT_TYPE_ITEM, item.getPutType().name());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int editItem(Item item) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE_ITEM, item.getType().name());
        values.put(KEY_PUT_TYPE_ITEM, item.getPutType().name());

        String where = KEY_ID_ITEM+" = ?";
        String[] whereArgs = {item.getIdItem()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteAnimal(Item item) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_ITEM+" = ?";
        String[] whereArgs = {item.getIdItem()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Item getAnimal(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        Item a=new Item("",null,null);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_ITEM+"="+id, null);
        if (c.moveToFirst()) {
            a.setIdItem(c.getString(c.getColumnIndex(KEY_ID_ITEM)));
            a.setType(Enum.valueOf(ItemType.class, c.getString(c.getColumnIndex(KEY_TYPE_ITEM))));
            a.setPutType(Enum.valueOf(ItemPutType.class, c.getString(c.getColumnIndex(KEY_PUT_TYPE_ITEM))));

            c.close();
        }

        return a;
    }

    public Cursor getItems() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }



}
