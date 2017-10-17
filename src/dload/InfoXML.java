package dload;

import java.util.Date;

public class InfoXML {
    String docName;
    String changedDate;

    public InfoXML() {
    }

    public InfoXML(String docName, String changedDate) {
        this.docName = docName;
        this.changedDate = changedDate;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getChangeDate() {
        return changedDate;
    }

    public void setChangeDate(String changeDate) {
        this.changedDate = changeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfoXML)) return false;

        InfoXML infoXML = (InfoXML) o;

        if (!docName.equals(infoXML.docName)) return false;
        return changedDate.equals(infoXML.changedDate);

    }

    @Override
    public int hashCode() {
        int result = docName.hashCode();
        result = 31 * result + changedDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "InfoXML{" +
                "docName='" + docName + '\'' +
                ", createDate=" + changedDate +
                '}';
    }
}
