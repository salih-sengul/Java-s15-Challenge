package com.workintech.manager;

import com.workintech.reader.Reader;
import com.workintech.util.IdGenerator;

import java.util.HashMap;
import java.util.Map;

public class ReaderManager {

    private static ReaderManager instance;
    private Map<Long, Reader> readers = new HashMap<>();

    private ReaderManager() {
    }

    public static ReaderManager getInstance() {
        if (instance == null) {
            instance = new ReaderManager();
        }
        return instance;
    }

    public Map<Long, Reader> getReaders() {
        return readers;
    }

    public Reader findReader(String readerName) {
        for (Map.Entry<Long, Reader> entry : readers.entrySet()) {
            if (entry.getValue().getName().equals(readerName)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public String addReader(Reader reader) {
        if (!readers.containsKey(reader.getId())) {
            readers.put(reader.getId(), reader);
            return "Reader added successfully";
        } else {
            return "Reader with this ID already exists";
        }
    }
}
