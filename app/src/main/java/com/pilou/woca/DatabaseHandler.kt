package com.pilou.woca

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSIOM) {

    //TODO : add a "favorite/learned" boolean to the "card" table

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $CARD_TABLE_NAME " +
                "($CARD_ID Integer PRIMARY KEY," +
                "$CARD_DECK_ID Integer, " +
                "$CARD_WORD TEXT, " +
                "$CARD_WORD_COLOR Integer," +
                "$CARD_WORD_EXAMPLE TEXT," +
                "$CARD_TRANSLATION_1 TEXT," +
                "$CARD_TRANSLATION_1_COLOR Integer," +
                "$CARD_TRANSLATION_1_EXAMPLE TEXT," +
                "$CARD_TRANSLATION_2 TEXT," +
                "$CARD_TRANSLATION_2_COLOR Integer," +
                "$CARD_TRANSLATION_2_EXAMPLE TEXT," +
                "$CARD_TRANSLATION_3 TEXT," +
                "$CARD_TRANSLATION_3_COLOR Integer," +
                "$CARD_TRANSLATION_3_EXAMPLE TEXT)")

        db?.execSQL("CREATE TABLE $DECK_TABLE_NAME " +
                "($DECK_ID Integer PRIMARY KEY," +
                "$DECK_LABEL TEXT, " +
                "$DECK_IMAGE_PATH_1 TEXT, " +
                "$DECK_IMAGE_PATH_2 TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE " + CARD_TABLE_NAME + ";")
        db.execSQL("DROP TABLE " + DECK_TABLE_NAME + ";")
        onCreate(db)
    }

    fun addCard(card: Card): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(CARD_DECK_ID, card.deck_id)
        values.put(CARD_WORD, card.word)
        values.put(CARD_WORD_COLOR, card.word_color)
        values.put(CARD_WORD_EXAMPLE, card.word_example)

        values.put(CARD_TRANSLATION_1, card.translation_1)
        values.put(CARD_TRANSLATION_1_COLOR, card.translation_1_color)
        values.put(CARD_TRANSLATION_1_EXAMPLE, card.translation_1_example)

        values.put(CARD_TRANSLATION_2, card.translation_2)
        values.put(CARD_TRANSLATION_2_COLOR, card.translation_2_color)
        values.put(CARD_TRANSLATION_2_EXAMPLE, card.translation_2_example)

        values.put(CARD_TRANSLATION_3, card.translation_3)
        values.put(CARD_TRANSLATION_3_COLOR, card.translation_3_color)
        values.put(CARD_TRANSLATION_3_EXAMPLE, card.translation_3_example)

        val _success = db.insert(CARD_TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedID", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    fun deleteCard(card: Card): Boolean {
        val db = this.writableDatabase
        return db.delete(CARD_TABLE_NAME, CARD_ID + "=" + card.id, null) > 0
    }

    fun getAllCards(): MutableList<Card> {
        var allCards = mutableListOf<Card>()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $CARD_TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var card = Card()

                    card.id = cursor.getString(cursor.getColumnIndex(CARD_ID)).toInt()
                    card.deck_id = cursor.getString(cursor.getColumnIndex(CARD_DECK_ID)).toInt()
                    card.word = cursor.getString(cursor.getColumnIndex(CARD_WORD))
                    card.word_color = cursor.getString(cursor.getColumnIndex(CARD_WORD_COLOR)).toInt()
                    card.word_example = cursor.getString(cursor.getColumnIndex(CARD_WORD_EXAMPLE))

                    card.translation_1 = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_1))
                    card.translation_1_color = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_1_COLOR)).toInt()
                    card.translation_1_example = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_1_EXAMPLE))

                    card.translation_2 = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_2))
                    card.translation_2_color = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_2_COLOR)).toInt()
                    card.translation_2_example = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_2_EXAMPLE))

                    card.translation_3 = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_3))
                    card.translation_3_color = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_3_COLOR)).toInt()
                    card.translation_3_example = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_3_EXAMPLE))

                    allCards.add(card)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allCards
    }

    fun getAllCardsString(): String {
        var allCards = ""
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $CARD_TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getString(cursor.getColumnIndex(CARD_ID))
                    val deck_id = cursor.getString(cursor.getColumnIndex(CARD_DECK_ID))
                    val word = cursor.getString(cursor.getColumnIndex(CARD_WORD))
                    val word_color = cursor.getString(cursor.getColumnIndex(CARD_WORD_COLOR))
                    val word_example = cursor.getString(cursor.getColumnIndex(CARD_WORD_EXAMPLE))

                    val translation_1 = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_1))
                    val translation_1_color = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_1_COLOR))
                    val translation_1_example = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_1_EXAMPLE))

                    val translation_2 = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_2))
                    val translation_2_color = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_2_COLOR))
                    val translation_2_example = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_2_EXAMPLE))


                    val translation_3 = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_3))
                    val translation_3_color = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_3_COLOR))
                    val translation_3_example = cursor.getString(cursor.getColumnIndex(CARD_TRANSLATION_3_EXAMPLE))

                    allCards = "$allCards\n$id $deck_id $word $word_color $word_example $translation_1 $translation_1_color $translation_1_example $translation_2 $translation_2_color $translation_2_example $translation_3 $translation_3_color $translation_3_example"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allCards
    }

    fun addDeck(deck: Deck): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(DECK_LABEL, deck.label)
        values.put(DECK_IMAGE_PATH_1, deck.img_path_1)
        values.put(DECK_IMAGE_PATH_2, deck.img_path_2)

        val _success = db.insert(DECK_TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedID", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    fun updateDeck(deck: Deck): Boolean {
        /*val db = this.writableDatabase
        val values = ContentValues()

        values.put(DECK_LABEL, deck.label)
        values.put(DECK_IMAGE_PATH_1, deck.img_path_1)
        values.put(DECK_IMAGE_PATH_2, deck.img_path_2)

        val _success = db.insert(DECK_TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedID", "$_success")
        return (Integer.parseInt("$_success") != -1)*/
        return true
    }

    fun deleteDeck(deck: Deck): Boolean {
        val db = this.writableDatabase
        return db.delete(DECK_TABLE_NAME, DECK_ID + "=" + deck.id, null) > 0
    }

    fun getAllDecks(): MutableList<Deck> {
        var allDecks = mutableListOf<Deck>()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $DECK_TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var deck = Deck()

                    deck.id = cursor.getString(cursor.getColumnIndex(DECK_ID)).toInt()
                    deck.label = cursor.getString(cursor.getColumnIndex(DECK_LABEL))
                    deck.img_path_1 = cursor.getString(cursor.getColumnIndex(DECK_IMAGE_PATH_1))
                    deck.img_path_2 = cursor.getString(cursor.getColumnIndex(DECK_IMAGE_PATH_2))

                    allDecks.add(deck)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        if (allDecks.size == 0) {
            var deck = Deck()
            deck.label = "Default label"
            addDeck(deck)
            allDecks.add(deck)
            Log.e("Dbhandler - getAllDecks", "Default deck created")
        }

        db.close()
        return allDecks
    }

    companion object {
        private val DB_NAME = "CardsDB"
        private val DB_VERSIOM = 4

        private val CARD_TABLE_NAME = "card"
        private val CARD_ID = "id"
        private val CARD_DECK_ID = "deck_id"
        private val CARD_WORD = "word"
        private val CARD_WORD_COLOR = "word_color"
        private val CARD_WORD_EXAMPLE = "word_example"

        private val CARD_TRANSLATION_1 = "translation_1"
        private val CARD_TRANSLATION_1_COLOR = "translation_1_color"
        private val CARD_TRANSLATION_1_EXAMPLE = "translation_1_example"

        private val CARD_TRANSLATION_2 = "translation_2"
        private val CARD_TRANSLATION_2_COLOR = "translation_2_color"
        private val CARD_TRANSLATION_2_EXAMPLE = "translation_2_example"

        private val CARD_TRANSLATION_3 = "translation_3"
        private val CARD_TRANSLATION_3_COLOR = "translation_3_color"
        private val CARD_TRANSLATION_3_EXAMPLE = "translation_3_example"

        private val DECK_TABLE_NAME = "deck"
        private val DECK_ID = "id"
        private val DECK_LABEL = "deck_label"
        private val DECK_IMAGE_PATH_1 = "deck_img_path_1"
        private val DECK_IMAGE_PATH_2 = "deck_img_path_2"

    }
}