package org.techcrumbs.search.management.exceptions;

import org.techcrumbs.search.model.Index;

public class IndexAlreadyExistsException extends IndexException {

    public IndexAlreadyExistsException(Index index) {
        super(index);
    }
}
