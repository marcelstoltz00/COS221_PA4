package com.mycompany.northwind_gui;

public class Clients{
    private int id;
    private String company;
    private String last_name;
    private String first_name;
    private String email_address;
    private String job_title;
    private String business_phone;
    private String home_phone;
    private String mobile_phone;
    private String fax_number;
    private String address;
    private String city;
    private String state_province;
    private String zip_postal_code;
    private String country_region;
    private String web_page;
    private String notes;
    private byte[] attachments;//check data type

    //def constr
    public Clients(){
    }

    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getCompany(){
        return company;
    }
    
    public void setCompany(String company){
        this.company = company;
    }
    
    public String getLastName(){
        return last_name;
    }
    
    public void setLastName(String last_name){
        this.last_name = last_name;
    }
    
    public String getFirstName(){
        return first_name;
    }
    
    public void setFirstName(String first_name){
        this.first_name = first_name;
    }
    
    public String getEmailAddress(){
        return email_address;
    }
    
    public void setEmailAddress(String email_address){
        this.email_address = email_address;
    }
    
    public String getJobTitle(){
        return job_title;
    }
    
    public void setJobTitle(String job_title){
        this.job_title = job_title;
    }
    
    public String getBusinessPhone(){
        return business_phone;
    }
    
    public void setBusinessPhone(String business_phone){
        this.business_phone = business_phone;
    }
    
    public String getHomePhone(){
        return home_phone;
    }
    
    public void setHomePhone(String home_phone){
        this.home_phone = home_phone;
    }
    
    public String getMobilePhone(){
        return mobile_phone;
    }
    
    public void setMobilePhone(String mobile_phone){
        this.mobile_phone = mobile_phone;
    }
    
    public String getFaxNumber(){
        return fax_number;
    }
    
    public void setFaxNumber(String fax_number){
        this.fax_number = fax_number;
    }
    
    public String getAddress(){
        return address;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    
    public String getCity(){
        return city;
    }
    
    public void setCity(String city){
        this.city = city;
    }
    
    public String getStateProvince(){
        return state_province;
    }
    
    public void setStateProvince(String state_province){
        this.state_province = state_province;
    }
    
    public String getZipPostalCode(){
        return zip_postal_code;
    }
    
    public void setZipPostalCode(String zip_postal_code){
        this.zip_postal_code = zip_postal_code;
    }
    
    public String getCountryRegion(){
        return country_region;
    }
    
    public void setCountryRegion(String country_region){
        this.country_region = country_region;
    }
    
    public String getWebPage(){
        return web_page;
    }
    
    public void setWebPage(String web_page){
        this.web_page = web_page;
    }
    
    public String getNotes(){
        return notes;
    }
    
    public void setNotes(String notes){
        this.notes = notes;
    }
    
    public byte[] getAttachments(){
        return attachments;
    }
    
    public void setAttachments(byte[] attachments){
        this.attachments = attachments;
    }
}