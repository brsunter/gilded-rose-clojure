# The Gilded Rose Kata with Clojure and Spec
This is my solution to the Gilded Rosa Refactoring kata.
The problem is to refactor a complicated piece of legacy code
without breaking any functionality.

My solution is:

  - Creating a spec of the data
  - Generative testing
  - Unit testing
  - Using a simple form of polymorphism to dispatch on type

---
This is the original problem:

Original Source: <http://iamnotmyself.com/2011/02/13/refactor-this-the-gilded-rose-kata/>

If you want to get cracking on the Clojure source then do this:

    git clone git@github.com:mjansen401/gilded-rose-clojure.git

Hi and welcome to team Gilded Rose.

As you know, we are a small inn with a prime location in a prominent city ran
by a friendly innkeeper named Allison.  We also buy and sell only the finest
goods. Unfortunately, our goods are constantly degrading in quality as they
approach their sell by date.

We have a system in place that updates our inventory for us. It was developed
by a no-nonsense type named Leeroy, who has moved on to new adventures. Your
task is to add the new feature to our system so that we can begin selling a
new category of items.

First an introduction to our system:

  - All items have a sell-in value which denotes the number of days we have to
    sell the item

  - All items have a quality value which denotes how valuable the item is

  - At the end of each day our system lowers both values for every item

Pretty simple, right? Well this is where it gets interesting:

  - Once the sell by date has passed, quality degrades twice as fast

  - The quality of an item is never negative

  - "Aged Brie" actually increases in quality the older it gets

  - The quality of an item is never more than 50

  - "Sulfuras", being a legendary item, never has to be sold or decreases in
    quality

  - "Backstage passes", like aged brie, increases in quality as it's sell-in
    value approaches; quality increases by 2 when there are 10 days or less
    and by 3 when there are 5 days or less but quality drops to 0 after the
    concert

We have recently signed a supplier of conjured items. This requires an update
to our system:

  - "Conjured" items degrade in quality twice as fast as normal items

Feel free to make any changes to the update-quality method and add any new code
as long as everything still works correctly. However, do not alter the item
function as that belongs to the goblin in the corner who will insta-rage and
one-shot you as he doesn't believe in shared code ownership.


Just for clarification, an item can never have its quality increase above 50,
however "Sulfuras" is a legendary item and as such its quality is 80 and it
never alters.

---

The first thing we do is create a spec for the data we're given.

These are the different cases of items we need to handle. Some of them are special cases
and some are the "common" items which will have the default behavior.

```
(s/def ::item-names #{"Aged Brie"
                      "Sulfuras, Hand Of Ragnaros"
                      "Backstage passes to a TAFKAL80ETC concert"
                      "Conjured Beanie"
                      "+5 Dexterity Vest"
                      "Elixir of the Mongoose"})
```

We create a spec that corresponds to the `:name` key in the given map and say it is a `string?` type.
Specs are just simple functions that take a value and return true if the value passes the spec.
`gen/one-of` will generate either a known item or a random string. We have to include a generator because we want to test both known items with special behavior and unknown item that have the default behaviour.

```
(s/def ::default-name string?)
(s/def ::name (s/with-gen
                string?
                #(gen/one-of [(s/gen ::default-name) (s/gen ::item-names)])))
```

We can see what these values look like with `gen/sample`
Spec will automatically generate values like empty string to try and find edge cases.

```
("" "Aged Brie" "Backstage passes to a TAFKAL80ETC concert" "2NP" "eN" "Sulfuras, Hand Of Ragnaros" "Sulfuras, Hand Of Ragnaros" "Y" "2Es6" "Aged Brie")
```

Sell in is a simple int which can be negative.

```
(s/def ::sell-in int?)
```

According to the documentation, quality should never be negative or greater than 50.

```
(s/def ::quality (s/int-in 0 51))
```

Finally we combine our specs to describe our input data.

```
(s/def ::item (s/keys :req-un [::sell-in ::name ::quality]))
(s/def ::items (s/coll-of ::item))
```

---

Next we will use generative testing on the original legacy code to validate our expectations.
The original `update-quality` method takes a list of items and returns an updated list of items.

```
(s/fdef update-quality
        :args (s/cat :items ::is/items)
        :ret ::is/items)
```

To test this, all we need to do is import this namespace into our test namespace and call `stest/check`
This function will use our spec to generate thousands of random input items that match our spec and fail if any items returned do not match our spec. This is a good way to put up some strong gaurdrails early on in the development process.

```
(deftest test-all
  (is (spec-passed? (st/check))))
```

We immediately find bugs in the original legacy code. Spec will find the smallest input that fails our spec and tell us exactly what the problem is.

```
:message "Specification-based check failed"
   :data {:clojure.spec/problems ({:path [:ret :quality], :pred (int-in-range? 0 51 %), :val -2, :via [:gilded-rose.items.spec/item :gilded-rose.items.spec/quality], :in [0 :quality]}), :clojure.spec.test/args ([{:sell-in 0, :name "Elixir of the Mongoose", :quality 0}]), :clojure.spec.test/val ({:sell-in -1, :name "Elixir of the Mongoose", :quality -2})
```

This error message tells us that the input `[{:sell-in 0, :name "Elixir of the Mongoose", :quality 0}]`
fails our `quality` spec. We can see that the quality drops below 0 for this input, which is not allowed. The returned value has a quality of -2

`({:sell-in -1, :name "Elixir of the Mongoose", :quality -2})`

We'll fix the original implementation and the the minimum neccesary work to make the test pass. We can add a simple check to make sure we never decrease this type of item below 0.

We find another interesting edge case for the backstage passes. It finds an edge case where if the backstage passes are in peak demand and have an existing quality of 48, the legacy code will increase the quality to 51, which is past our limit. We'll fix this with another conditional check in the legacy code.

```
:message "Specification-based check failed"
   :data {:clojure.spec/problems ({:path [:ret :quality], :pred (int-in-range? 0 51 %), :val 51, :via [:gilded-rose.items.spec/item :gilded-rose.items.spec/quality], :in [0 :quality]}), :clojure.spec.test/args ([{:sell-in 1, :name "Backstage passes to a TAFKAL80ETC concert", :quality 48}]), :clojure.spec.test/val ({:sell-in 0, :name "Backstage passes to a TAFKAL80ETC concert", :quality 51}), :clojure.spec/failure :check-failed}
```

One of the most difficult parts of unit testing is thinking of these failing edge cases ahead of time, but spec can find a lot of these for us automatically.

---

Now we'll refactor the code.

We'll use a simple form of polymorphism called multimethods to allow each item type to have custom behaviour. We say that the name of the item will determine it's behaviour.

`(defmulti update-item :name)`

When we call `update-item` on an item map, clojure will use the :name field to determine which function to call. We can register functions using `defmethod` to handle the different names.

We create an `items.item` namespace with the defmulti declaration, the default item implementation, and some pure helper functions that all items can use.

```
(def max-quality 50)
(def min-quality 0)

(defn decrease-quality
  [{:keys [quality] :as item} x]
  (let [next-quality (- quality x)]
    (if (>= next-quality min-quality)
      (assoc item :quality next-quality)
      (assoc item :quality min-quality))))

(defn increase-quality
  [{:keys [quality] :as item} x]
  (let [next-quality (+ quality x)]
    (if (<= next-quality max-quality)
      (assoc item :quality next-quality)
      (assoc item :quality max-quality))))
```
Most of the logic involves increasing and decreasing the quality of different items. We include some helper functions that won't let you change the quality beyond our min and max.

```
(defn default-update-quality
  [{:keys [sell-in] :as item}]
  (if (neg? sell-in)
    (decrease-quality item 2)
    (decrease-quality item 1)))

(defmethod update-item :default
  [item]
  (-> (default-update-quality item)
      (update :sell-in dec)))
```

Here we register a defmethod with the special `:default` key, which will be called if no other defmethod matches the item name. This will handle all the "common" items. Here the default behaviour is to decrease the quality by 1 or by 2 if the sell in date is negative. Our `decrease` quality helper function will prevent us from making the quality negative and allow us to avoid nested conditionals.

We will still write unit tests even though we have generative testing. Testing is extremely simple in clojure. We just create some test fixtures and validate our expectations.

```
(def normal-item
  {:name "random"
   :quality 5
   :sell-in 2})

(def day-zero-item
  {:name "item"
   :quality 9
   :sell-in 0})

(def expired-item
  {:name "Random Item"
   :quality 5
   :sell-in -2})

(def low-expired-item
  {:name "Foo"
   :quality 1
   :sell-in -2})

(deftest test-item
  (testing "Default item decreases by 1"
    (is (= 4 (:quality (i/update-item normal-item)))))
  (testing "Default item decreases by 1 on day 0"
    (is (= 8 (:quality (i/update-item day-zero-item)))))
  (testing "Default item decreases by 2 after sell-in-date"
    (is (= 3 (:quality (i/update-item expired-item)))))
  (testing "Cojured quality is never negative"
    (is (= 0 (:quality (i/update-item low-expired-item))))))
```

### Gilded Rose In Other Languages

  - Ruby - <http://github.com/professor/GildedRose>
  - JavaScript - <https://github.com/guyroyse/gilded-rose-javascript>
  - C# - <https://github.com/NotMyself/GildedRose>
  - Java - <https://github.com/sandromancuso/gilded_rose_kata>
