public class WordDocument implements Document{
    public void open(){
        System.out.println("Opening Word document!");
    }
    public void save(){
        System.out.println("Saving Word Document!");
    }
    public void close(){
        System.out.println("Closing Word document!");
    }
}