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
	public List<String> shingleImpl(String text, boolean isQuery) throws IOException {
		Result parse;
		if (isQuery) {
			parse = NlpAnalysis.parse(text);
		} else {
			parse = IndexAnalysis.parse(text);
		}
		Set<String> words = new HashSet<String>();
		for (Term term : parse.getTerms()) {
			words.add(term.getName());
		}

		return new ArrayList<String>(words);
	}

}
