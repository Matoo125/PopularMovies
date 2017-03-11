package com.example.matej.popularmoviesdemo;

/**
 * Created by matej on 5.3.2017.
 * true
 */
//
//public class MoviesProvider extends ContentProvider {
//
//    static final String AUTHORITY = "com.example.matej.popularmoviesdemo.MoviesProvider";
//    private static final String MOVIES_TABLE = DatabaseHandler.TABLE_MOVIE;
//    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + MOVIES_TABLE);
//
//    private DatabaseHandler mDB;
//
//
//    static final int MOVIES = 100;
//    static final int MOVIE_ID = 200;
//
//
//    static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//
//    static {
//        sUriMatcher.addURI(AUTHORITY, MOVIES_TABLE, MOVIES);
//        sUriMatcher.addURI(AUTHORITY, MOVIES_TABLE + "/#", MOVIE_ID);
//    }
//
//
//    @Override
//    public boolean onCreate() {
//        mDB = new DatabaseHandler(getContext());
//        return false;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
//
//        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
//        queryBuilder.setTables(MOVIES_TABLE);
//
//        int uriType = sUriMatcher.match(uri);
//
//        switch (uriType) {
//            case MOVIE_ID:
//                queryBuilder.appendWhere(DatabaseHandler.KEY_ID + "=" + uri.getLastPathSegment());
//                break;
//            case MOVIES:
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown uri");
//        }
//
//        Cursor cursor = queryBuilder.query(mDB.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
//        cursor.setNotificationUri(getContext().getContentResolver(), uri);
//
//
//        return cursor;
//    }
//
//    @Nullable
//    @Override
//    public String getType(@NonNull Uri uri) {
//
//        return null;
//
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
//
//        int uriType = sUriMatcher.match(uri);
//        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
//
//        long id = 0;
//        switch (uriType) {
//            case MOVIES:
//                id = sqlDB.insert(MOVIES_TABLE, null, values);
//                break;
//            default:
//                throw new IllegalArgumentException("Unknowh URI: " + uri);
//        }
//        getContext().getContentResolver().notifyChange(uri, null);
//        return Uri.parse(MOVIES_TABLE + "/" + id);
//
//    }
//
//    @Override
//    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
//        int uriType = sUriMatcher.match(uri);
//        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
//        int rowsDeleted = 0;
//
//        switch (uriType) {
//            case MOVIES:
//                rowsDeleted = sqlDB.delete(MOVIES_TABLE,
//                        selection,
//                        selectionArgs);
//                break;
//
//            case MOVIE_ID:
//                String id = uri.getLastPathSegment();
//                if (TextUtils.isEmpty(selection)) {
//                    rowsDeleted = sqlDB.delete(MOVIES_TABLE,
//                            DatabaseHandler.KEY_ID + "=" + id,
//                            null);
//                } else {
//                    rowsDeleted = sqlDB.delete(MOVIES_TABLE,
//                            DatabaseHandler.KEY_ID + "=" + id
//                                    + " and " + selection,
//                            selectionArgs);
//                }
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown URI: " + uri);
//        }
//        getContext().getContentResolver().notifyChange(uri, null);
//        return rowsDeleted;
//    }
//
//    @Override
//    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
//
//        int uriType = sUriMatcher.match(uri);
//        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
//        int rowsUpdated = 0;
//
//        switch (uriType) {
//            case MOVIES:
//                rowsUpdated = sqlDB.update(MOVIES_TABLE, values, selection, selectionArgs);
//                break;
//            case MOVIE_ID:
//                String id = uri.getLastPathSegment();
//                if (TextUtils.isEmpty(selection)) {
//                    rowsUpdated = sqlDB.update(MOVIES_TABLE, values, DatabaseHandler.KEY_ID, null);
//                } else {
//                    rowsUpdated = sqlDB.update(MOVIES_TABLE, values, DatabaseHandler.KEY_ID + "=" + " and " + selection, selectionArgs);
//                }
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown URI: " + uri);
//        }
//        getContext().getContentResolver().notifyChange(uri, null);
//
//        return rowsUpdated;
//    }
//}
