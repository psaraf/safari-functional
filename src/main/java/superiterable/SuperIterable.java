package superiterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SuperIterable<E> implements Iterable<E> {
  private Iterable <E> self;

  public SuperIterable(Iterable<E> self) {
    this.self = self;
  }

//  public void forEvery(Consumer<E> op) {
////    for (E e : self) {
////      op.accept(e);
////    }
////  }

  // Functor
  public <F> SuperIterable<F> map(Function<E, F> op) {
    List<F> out = new ArrayList<>();

    for (E e : self) {
      F f = op.apply(e);
      out.add(f);
    }

    return new SuperIterable<>(out);
  }

  // Monad
  // can be used for map and filter
  public <F> SuperIterable<F> flatMap(Function<E, SuperIterable<F>> op) {
    List<F> out = new ArrayList<>();

    for (E e : self) {
      SuperIterable<F> manyf = op.apply(e);
      for (F f : manyf) {
        out.add(f);
      }
    }

    return new SuperIterable<>(out);
  }

  public SuperIterable<E> filter(Predicate<E> crit) {
    List<E> out = new ArrayList<>();
    for (E s : self) {
      if (crit.test(s)) {
        out.add(s);
      }
    }
    return new SuperIterable<>(out);
  }

  @Override
  public Iterator<E> iterator() {
    return self.iterator();
  }
}
