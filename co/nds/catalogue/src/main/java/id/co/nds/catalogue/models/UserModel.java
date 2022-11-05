package id.co.nds.catalogue.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserModel extends RecordModel{
    //main
    private Integer id;

    @NotEmpty(message = "User fulname is required")
    private String fullName;
    @NotEmpty(message = "User role id is required")
    private String roleId;

    @Pattern(regexp = "^(\\+62|0)\\d{9,12}",
    message = "User call numbers start with 0 or +62 followed by 9 - 12 digits number")
    private String callNumber;

    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getCallNumber() {
        return callNumber;
    }
    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }
}
