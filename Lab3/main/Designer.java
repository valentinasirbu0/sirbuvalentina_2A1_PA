package main.java.model;

public class Designer extends Person {

    private DesignerType designerType;

    public Designer(DesignerType type, String name, String date) {
        System.out.println("Designer created");
        this.designerType = type;
        this.setName(name);
        this.setBirthDate(date);
    }

    public DesignerType getTypeOfDesigner() {

        return designerType;
    }

    public void setTypeOfDesigner(DesignerType designerType) {

        this.designerType = designerType;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Designer{" +
                "name=" + getName() +
                ", designerType=" + designerType +
                ", degree=" + getDegree() +
                '}';
    }
}
