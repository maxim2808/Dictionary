package dictionary_client.dictionary_client.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeTestService {
    @Value("${countWordInTest}")
    int countWordInTest;








}
