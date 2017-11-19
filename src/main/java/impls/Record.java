package impls;

/**
 * This is the POJO (Plain Old Java Object) that is being used for all the operations.
 * As long as all fields are public or have a getter/setter, the system can handle them.
 */
public class Record {
    //fields: Name,Location,Extension,Email,Title,Department,Dept ID
    private String name;
    private String location;
    private int extension;
    private String email;
    private String title;
    private String department;
    private int deptID;

    public Record() {

    }

    public Record(String name, String location, int extension, String email, String title,
                  String department, int deptID) {
        this.name = name;
        this.location = location;
        this.extension = extension;
        this.email = email;
        this.title = title;
        this.department = department;
        this.deptID = deptID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getExtension() {
        return extension;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getDepartment() {
        return department;
    }

    public int getDeptID() {
        return deptID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setExtension(int extension) {
        this.extension = extension;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setDeptID(int deptID) {
        this.deptID = deptID;
    }
}
