package org.deeplearning4j.models.fasttext;

import com.github.jfasttext.JFastText;
import lombok.Builder;
import lombok.Data;
import org.deeplearning4j.models.sequencevectors.SequenceVectors;
import org.deeplearning4j.models.word2vec.VocabWord;

public class FastText extends SequenceVectors<VocabWord> {

    private JFastText fastTextImpl;
    private Builder builder;

    public FastText() {
        fastTextImpl = new JFastText();
    }

    public FastText(Builder builder) {
        this();
        this.builder = builder;
    }

    @Override
    public void fit() {

        String[] cmd;
        if (builder.supervised)
            cmd = new String[]{"supervised", "-input", builder.inputFile,
                    "-output", builder.outputFile};
        else
            cmd = new String[]{"-input", builder.inputFile,
                    "-output", builder.outputFile};

        fastTextImpl.runCmd(cmd);
    }

    public String predict(String modelPath, String text) {
        fastTextImpl.loadModel(modelPath);
        String label = fastTextImpl.predict(text);
        return label;
    }

    @Data
    @lombok.Builder
    public static class Builder {
        private boolean supervised;
        private boolean quantize;
        private boolean predict;
        private boolean predict_prob;
        private boolean skipgram;
        private boolean cbow;
        private boolean nn;
        private boolean analogies;

        private String inputFile;
        private String outputFile;
    }
}
