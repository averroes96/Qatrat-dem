package com.averroes.qatartdem.includes;

public interface StoragePermissions {

    static final int STORAGE_REQUEST = 300;

    boolean checkStoragePermission();

    void requestStoragePermission();

}
