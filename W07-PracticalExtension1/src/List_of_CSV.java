import java.util.ArrayList;

public class List_of_CSV {
    private int passengerId, survived, pClass, sibSp, parch;
    private String name, sex, ticket, cabin, embarked = null;
    private double fare;
    private Double age = null;

    public List_of_CSV(ArrayList<String> fields){
        if(fields.size() == 12){
            this.embarked = fields.get(11);
        }
        this.passengerId = Integer.parseInt(fields.get(0));
        this.sibSp = Integer.parseInt(fields.get(6));
        this.parch = Integer.parseInt(fields.get(7));
        this.survived = Integer.parseInt(fields.get(1));
        this.pClass = Integer.parseInt(fields.get(2));
        this.fare = Double.parseDouble(fields.get(9));
        this.name = fields.get(3);
        this.sex = fields.get(4);
        this.ticket = fields.get(8);
        nullCheck(fields.get(5), fields.get(10));
    }

    /**public List_of_CSV(String passengerId, String survived, String pClass, String name, String sex, String age,String sibSp,
                       String parch, String ticket, String fare, String cabin, String embarked){
        this.passengerId = Integer.parseInt(passengerId);
        this.sibSp = Integer.parseInt(sibSp);
        this.parch = Integer.parseInt(parch);
        this.survived = Integer.parseInt(survived);
        this.pClass = Integer.parseInt(pClass);
        this.embarked = embarked;
        this.fare = Double.parseDouble(fare);
        this.name = name;
        this.sex = sex;
        this.ticket = ticket;
        nullCheck(age, cabin);
    }*/

    public void nullCheck(String age, String cabin){
        if(!age.isEmpty()){
            this.age = Double.parseDouble(age);
        }

        if(!cabin.isEmpty()){
            this.cabin = cabin;
        }
    }

    public int getParch() {
        return parch;
    }

    public int getSibSp() {
        return sibSp;
    }

    public int getPassengerId(){
        return passengerId;
    }

    public double getAge() {
        return age == null ? 0.0 : age.doubleValue();
//        System.out.println(age);
//        return age;
    }

    public int getpClass() {
        return pClass;
    }

    public double getFare() {
        return fare;
    }

    public String getName() {
        return name;
    }

    public int getSurvived() {
        return survived;
    }

    public String getCabin() {
        return cabin;
    }

    public String getEmbarked() {
        return embarked;
    }

    public String getSex() {
        return sex;
    }

    public String getTicket() {
        return ticket;
    }
}
