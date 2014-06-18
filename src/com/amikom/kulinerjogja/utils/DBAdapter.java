/*
 * Copyright (C) PT. Polatic Informatika Indonesia
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.amikom.kulinerjogja.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.amikom.kulinerjogja.model.UserModel;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBAdapter extends SQLiteAssetHelper {
	private static final String DB_NAME = "db";
	private static final int DB_VER = 1;
	private static final String TABLE_USER = "tb_user";
	private static final String FIELD_USER = "user";
	private static final String FIELD_PASS = "password";

	private static DBAdapter dbInstance = null;
	private static SQLiteDatabase db;

	public DBAdapter(Context context) {
		super(context, DB_NAME, null, DB_VER);
	}

	public static DBAdapter getInstance(Context context) {
		if (dbInstance == null) {
			dbInstance = new DBAdapter(context);
			db = dbInstance.getWritableDatabase();
		}
		return dbInstance;
	}

	@Override
	public synchronized void close() {
		super.close();
		if (dbInstance != null) {
			dbInstance.close();
		}
	}
	
	public boolean insertUser(UserModel model){
		db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(FIELD_USER, model.getUser());
		contentValues.put(FIELD_PASS, model.getPassword());
		long countInsert = db.insert(TABLE_USER, null, contentValues);
		return (countInsert != -1) ? true : false;
	}
	
	public List<UserModel> selectUser(){
		List<UserModel> userModels = new ArrayList<UserModel>();
		Cursor cursor = db.query(TABLE_USER, new String[] {
				FIELD_USER, FIELD_PASS }, null, null, null, null, null);

			if (cursor.moveToFirst()) {
				do {
					UserModel model = new UserModel();
					model.setUser(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_USER)));
					model.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_PASS)));

					userModels.add(model);
				} while (cursor.moveToNext());
			}
		return userModels;
	}

}
