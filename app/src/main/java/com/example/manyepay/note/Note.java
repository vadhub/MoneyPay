package com.example.manyepay.note;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int idNote;
    private String nameProduct;
    private int summ;
    private String date;
    private String valute;

    public Note(int idNote, String nameProduct, int summ, String date, String valute) {
        this.idNote = idNote;
        this.nameProduct = nameProduct;
        this.summ = summ;
        this.date = date;
        this.valute = valute;
    }

    @Ignore
    public Note(String nameProduct, int summ, String date, String valute) {
        this.nameProduct = nameProduct;
        this.summ = summ;
        this.date = date;
        this.valute = valute;
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getSumm() {
        return summ;
    }

    public void setSumm(int summ) {
        this.summ = summ;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValute() {
        return valute;
    }

    public void setValute(String valute) {
        this.valute = valute;
    }
}
