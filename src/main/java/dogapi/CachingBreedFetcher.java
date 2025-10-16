package dogapi;

import java.io.IOException;
import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    BreedFetcher fetcher;
    private Map<String, List<String>> cache = new HashMap<>();

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws IOException, BreedNotFoundException {
        // return statement included so that the starter code can compile and run.
        List<String> subBreeds;
        if (cache.containsKey(breed)) {
            subBreeds = cache.get(breed);
        } else {
            callsMade += 1;
            subBreeds = fetcher.getSubBreeds(breed);
            if (subBreeds.isEmpty()) {
                throw new BreedNotFoundException(breed);
            } else {
                cache.put(breed, subBreeds);
                return subBreeds;
            }
        }
        return subBreeds;

    }

    public int getCallsMade() {
        return callsMade;
    }
}