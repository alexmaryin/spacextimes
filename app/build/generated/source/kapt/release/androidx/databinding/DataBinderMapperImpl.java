package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new ru.alexmaryin.spacextimes_rx.DataBinderMapperImpl());
  }
}
