package com.example.raovat.sell;

public interface OnSendData {
    void sendPostName(String postName);

    void sendPriceSdt(String price,String sdt);

    void sendDescription(String description);

    void sendIDAddress(String id, String address);
    void sendAddress( String address);

    void sendCategoryChildId(String categoryChildId,String name);

    void sendCategoryParents(String name);


}
