package models;

/**
 *
 * @author Suguru
 */
public class Item {

    private String id;
    private String todolistid;
    private String itemname;
    private String due;
    private boolean important;
    private boolean finished;

    public Item() {
    }

    public Item(String todolistid, String itemname, String due, boolean important, boolean finished) {
        this.todolistid = todolistid;
        this.itemname = itemname;
        this.due = due;
        this.important = important;
        this.finished = finished;
    }

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
    public String getTodolistid() {
        return todolistid;
    }

    /**
     * @param todolistid the todolistid to set
     */
    public void setTodolistid(String todolistid) {
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
     * @return the due
     */
    public String getDue() {
        return due;
    }

    /**
     * @param due the due to set
     */
    public void setDue(String due) {
        this.due = due;
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

    /**
     * @return the finished
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * @param finished the finished to set
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

}
