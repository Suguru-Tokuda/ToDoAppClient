package models;

/**
 *
 * @author Suguru
 */
public class ToDoList {

    private String id;
    private String todolistname;
    private String createdate;
    private boolean active;

    public ToDoList() {
    }

    public ToDoList(String todolistname, boolean active) {
        this.todolistname = todolistname;
    }
    
    public ToDoList(String todolistname, String createdate, boolean active) {
        this.todolistname = todolistname;
        this.createdate = createdate;
        this.active = active;
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

    /**
     * @return the todolistname
     */
    public String getTodolistname() {
        return todolistname;
    }

    /**
     * @param todolistname the todolistname to set
     */
    public void setTodolistname(String todolistname) {
        this.todolistname = todolistname;
    }

    /**
     * @return the createdate
     */
    public String getCreatedate() {
        return createdate;
    }

    /**
     * @param createdate the createdate to set
     */
    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

}
