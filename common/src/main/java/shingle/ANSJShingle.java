package shingle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;

public class ANSJShingle extends MicroblogShingle {

    public ANSJShingle(StopWordFilter filter) {
        super(filter);
    }

    @Override
    public List<String> shingleImpl(String text, boolean isQuery)
            throws IOException {
        Result parse;
        if (isQuery) {
            parse = NlpAnalysis.parse(text);
        } else {
            parse = IndexAnalysis.parse(text);
        }
        Set<String> words = new HashSet<String>();
        for (Term term : parse) {
            words.add(term.getName());
        }

        return new ArrayList<String>(words);
    }

    public static void main(String[] args) {
        Result terms = NlpAnalysis
                .parse("如家方面跟我有了进一步的沟通，我要求如家答应以下要求： 1、配合警方积极取证，提供线索，帮助侦破 2、承认和颐酒店对我受害的结果跟踪明显不负责任； 3、承认集团的管理有严重错误； 4、承认和颐酒店安保工作有严重问题； 我发微博的初衷是为了解决问题，合理维权。我没有接受也不接受任何形式的经...全文： ");
        for (Term term : terms) {
            System.out.println(term);
        }
    }

}
