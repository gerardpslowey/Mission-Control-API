package dataTypes;

public class Report {
    
    String content;
    int size;

    public Report(String content, int size){
        this.content = content;
        this.size = size;
    }
    // Reports can be telemetry (100-10k bytes, frequent) 
    // or data (100k-100MB, periodic)

    @Override
    public String toString(){
        return content;
    }
}
