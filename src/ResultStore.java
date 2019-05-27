package kurz.java.taxdecoder;

public class ResultStore 
{
    private String dateCreate;
    private String dateDocument;
    private String idDoc;
    private String innUL;
    private String orgName;
    private String income;
    private String expense;

    private String record;
    private final String splitter = ";";
    
    public ResultStore(){}
    
    public void setDateCreate(String dateCreate) { this.dateCreate = dateCreate; }
    public String getDateCreate() {return this.dateCreate; }
    
    public void setDateDocument(String dateDocument) { this.dateDocument = dateDocument; }
    public String getDateDocument() {return this.dateDocument; }
    
    public void setIdDoc(String idDoc) { this.idDoc = idDoc; }
    public String getIdDoc() {return this.idDoc; }
    
    public void setInnUL(String innUL) { this.innUL = innUL; }
    public String getInnUL() {return this.innUL; }
    
    public void setOrgName(String orgName) { this.orgName = orgName; }
    public String getOrgName() {return this.orgName; }
    
    public void setIncome(String income) { this.income = income; }
    public String getIncome() {return this.income; }
    
    public void setExpense(String expense) { this.expense = expense; }
    public String getExpense() { return this.expense; }
    
    public String getRecord()
    {
        StringBuilder sb = new StringBuilder()
                .append(dateCreate).append(splitter)       
                .append(dateDocument).append(splitter)
                .append(idDoc).append(splitter)
                .append(innUL).append(splitter)
                .append(orgName).append(splitter)
                .append(income).append(splitter)
                .append(expense).append(splitter).append("\n");
        return sb.toString();
    }
    
    public boolean compareINN(String inn)
    {
        return inn.equals(this.innUL);
    }
}
