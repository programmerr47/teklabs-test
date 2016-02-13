package api.service;

import java.util.List;

public interface ListConverter<I, O> {

    List<O> convert(List<I> input);

}
