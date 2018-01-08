package com.example.ef.databasecurseradapter.model;

/**
 * Created by EF on 27-Nov-17.
 */

public class Note {
    private  String id;
    private String notes;
    private String time;

    public Note(){

    }

    public Note(String id, String notes, String time) {
        this.id = id;
        this.notes = notes;
        this.time = time;
    }

    public String getId(){
        return id;
    }

    public String getNotes() {
        return notes;
    }

    public String getTime() {
        return time;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
