package com.gretel.anticorruption.model.Report;

public class Report {

    private String user;
    private String id;
    private String officer;
    private String authority;
    private String place;
    private String date;
    private String report;
    private String author;
    private Long up;
    private Long down;
    private Long rank;
    private Long time;

    public Report(){

    }

    public Report(String author, String officer, String authority, String place, String reportDate, String report){
        this.id = "0000 0000 0000 0000";
        this.author = author;
        this.officer = officer;
        this.authority = authority;
        this.place = place;
        this.date = reportDate;
        this.report = report;
        up = 0L;
        down = 0L;
        rank = 0L;
        time = 0 - System.currentTimeMillis();
    }

    public String getUser() {
        return  user; }

    public String getOfficer(){
        return officer;
    }

    public String getAuthority(){
        return authority;
    }

    public String getPlace(){
        return place;
    }

    public String getDate(){
        return date;
    }

    public String getReport(){
        return report;
    }

    public String getAuthor() { return author; }

    public Long getUp() {
        return up; }

    public Long getDown() {
        return down;
    }

    public Long getRank() {
        return rank;
    }

    public Long getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setOfficer(String officer){
        this.officer = officer;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }

    public void setPlace(String place){
        this.place = place;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setReport(String report){
        this.report = report;
    }

    public void setAuthor(String author) { this.author = author; }

    public void setUp(Long up) { this.up = up; }

    public void setDown(Long down) { this.down = down; }

    public void setRank(Long rank) { this.rank = rank; }

    public void setTime(Long time) { this.time = time; }

    public void setId(String id) { this.id = id; }

    public void upvote(){
        up++;rank--;
    }

    public void downvote(){
        down++;rank++;
    }

}
