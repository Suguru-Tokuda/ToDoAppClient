package models;

/**
 *
 * @author Suguru
 */
public class ToDoList {

    private String id;
    private String todolistname;
    private boolean active;

    public ToDoList() {
    }

    public ToDoList(String listname, boolean active) {
        this.todolistname = listname;
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
     * @return the todolistname
     */
    public String getToDoListname() {
        return todolistname;
    }

    /**
     * @param listname the todolistname to set
     */
    public void setListname(String todolistname) {
        this.todolistname = todolistname;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

}
