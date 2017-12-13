package models;

/**
 *
 * @author Suguru
 */
public class Item {

    private String id;
    private String todolistid;
    private String itemname;
    private String date;
    private boolean important;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the todolistid
     */
    public String getToDoListid() {
        return todolistid;
    }

    /**
     * @param todolistid the todolistid to set
     */
    public void setToDoListid(String todolistid) {
        this.todolistid = todolistid;
    }

    /**
     * @return the itemname
     */
    public String getItemname() {
        return itemname;
    }

    /**
     * @param itemname the itemname to set
     */
    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the important
     */
    public boolean isImportant() {
        return important;
    }

    /**
     * @param important the important to set
     */
    public void setImportant(boolean important) {
        this.important = important;
    }

}
