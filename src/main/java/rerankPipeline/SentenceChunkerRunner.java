package rerankPipeline;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunking;
import com.aliasi.sentences.SentenceChunker;
import com.aliasi.sentences.SentenceModel;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/** Use SentenceModel to find sentence boundaries in text */
public class SentenceChunkerRunner {

  static final TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;
  static final SentenceModel SENTENCE_MODEL = new LegalSentenceModel();
  static final SentenceChunker SENTENCE_CHUNKER = new SentenceChunker(
      TOKENIZER_FACTORY, SENTENCE_MODEL);
  
  public static List<String> getSentences() throws IOException{
    ArrayList<String> ret = new ArrayList<String>();
    File file = new File("input/raw");// args[0]);
    String text = Files.readFromFile(file, "ISO-8859-1");
    System.out.println(text);

    Chunking chunking = SENTENCE_CHUNKER.chunk(text.toCharArray(), 0,
        text.length());
    Set<Chunk> sentences = chunking.chunkSet();
    if (sentences.size() < 1) {
      System.out.println("No sentence chunks found.");
      return ret;
    }
    String slice = chunking.charSequence().toString();
    for (Iterator<Chunk> it = sentences.iterator(); it.hasNext();) {
      Chunk sentence = it.next();
      int start = sentence.start();
      int end = sentence.end();
      ret.add(slice.substring(start, end));
    }
    return ret;
  }

  public static void main(String[] args) throws IOException {
    File file = new File("input/raw");// args[0]);
    String text = Files.readFromFile(file, "UTF-8");

    Chunking chunking = SENTENCE_CHUNKER.chunk(text.toCharArray(), 0,
        text.length());
    Set<Chunk> sentences = chunking.chunkSet();
    if (sentences.size() < 1) {
      System.out.println("No sentence chunks found.");
      return;
    }
    String slice = chunking.charSequence().toString();
    int i = 1;
    for (Iterator<Chunk> it = sentences.iterator(); it.hasNext();) {
      Chunk sentence = it.next();
      int start = sentence.start();
      int end = sentence.end();
      System.out.println("SENTENCE " + (i++) + ":");
      System.out.println(slice.substring(start, end));
    }
  }
}
