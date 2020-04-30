package eg.edu.alexu.csd.filestructure.btree;

import javax.management.RuntimeErrorException;
import java.io.File;
import java.util.List;

public class SearchEngine implements ISearchEngine {

    public SearchEngine(){

    }
    @Override
    public void indexWebPage(String filePath) {
        if(isNullOrEmpty(filePath)){
            throw new RuntimeErrorException(new Error());
        }
        File file =  new File(filePath);
        if(!file.exists()){
            throw new RuntimeErrorException(new Error());
        }

    }

    @Override
    public void indexDirectory(String directoryPath) {
        if(isNullOrEmpty(directoryPath)){
            throw new RuntimeErrorException(new Error());
        }
        File directory =  new File(directoryPath);
        if (!directory.exists()){
            throw new RuntimeErrorException(new Error());
        }


    }

    @Override
    public void deleteWebPage(String filePath) {
        if(isNullOrEmpty(filePath)){
            throw new RuntimeErrorException(new Error());
        }
        File file =  new File(filePath);
        if(!file.exists()){
            throw new RuntimeErrorException(new Error());
        }

    }

    @Override
    public List<ISearchResult> searchByWordWithRanking(String word) {
        return null;
    }

    @Override
    public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
        return null;
    }
    private boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty() && !str.trim().isEmpty()){
            return false;
        }
        return true;
    }
}
