package app.models;

public class Repair {
    
    private int id;
    private int acceptorId;
    private int masterId;
    private String masterComments;
    private String diagnosticResult;
    private String repairResult;
    private int statusId;
    private String dateOfAccept;
    private String dateOfGiveOut;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(int acceptorId) {
        this.acceptorId = acceptorId;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public String getMasterComments() {
        return masterComments;
    }

    public void setMasterComments(String masterComments) {
        this.masterComments = masterComments;
    }

    public String getDiagnosticResult() {
        return diagnosticResult;
    }

    public void setDiagnosticResult(String diagnosticResult) {
        this.diagnosticResult = diagnosticResult;
    }

    public String getRepairResult() {
        return repairResult;
    }

    public void setRepairResult(String repairResult) {
        this.repairResult = repairResult;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getDateOfAccept() {
        return dateOfAccept;
    }

    public void setDateOfAccept(String dateOfAccept) {
        this.dateOfAccept = dateOfAccept;
    }

    public String getDateOfGiveOut() {
        return dateOfGiveOut;
    }

    public void setDateOfGiveOut(String dateOfGiveOut) {
        this.dateOfGiveOut = dateOfGiveOut;
    }
}
