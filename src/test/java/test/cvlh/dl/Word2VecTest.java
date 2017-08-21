package test.cvlh.dl;

import com.cvlh.util.NlpUtil;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class Word2VecTest {
    public static void main(String[] args) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        Files.readAllLines(Paths.get("D:/corpus.txt")).forEach(s -> {
            stringBuilder.append(s);
        });
        List<Object> list = NlpUtil.tokenize(stringBuilder.toString(), false);
        list.forEach(o -> {
            builder.append(o.toString()).append(" ");
        });

        Files.write(Paths.get("E:/corpus.txt"), builder.toString().getBytes());
        String filePath = new File("E:/corpus.txt").getAbsolutePath();
        System.out.println("Load & Vectorize Sentences....");
        // Strip white space before and after for each line
        SentenceIterator iter = new BasicLineIterator(filePath);
        // Split on white spaces in the line to get words
//        TokenizerFactory t = new DefaultTokenizerFactory();
        TokenizerFactory t = new DefaultTokenizerFactory();

        t.setTokenPreProcessor(new CommonPreprocessor());
        System.out.println("Building model....");
        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(2)
                .iterations(1)
                .layerSize(100)
                .seed(42)
                .windowSize(5)
                .iterate(iter)
                .tokenizerFactory(t)
                .build();

        System.out.println("Fitting Word2Vec model....");
        vec.fit();

        // Write word vectors
        WordVectorSerializer.writeWord2VecModel(vec, "D:/pathToWriteto.txt");

        System.out.println("Closest Words:");
        Collection<String> lst = vec.wordsNearest("人工智能", 10);
        System.out.println(lst);

        double cosSim = vec.similarity("华尔街", "金融");
        System.out.println(cosSim);
    }
}
