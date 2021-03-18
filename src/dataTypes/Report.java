package dataTypes;

public class Report {
    
    String content;

    public Report(String content){
        this.content = content;
    }
    // Reports can be telemetry (100-10k bytes, frequent) 
    // or data (100k-100MB, periodic)

    @Override
    public String toString(){
        return content;
    }
}
