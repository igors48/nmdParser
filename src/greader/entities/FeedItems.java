package greader.entities;

import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 27.07.2011
 */
public class FeedItems {

    private Item[] items;

    public Item[] getItems() {
        return this.items;
    }

    public void setItems(final Item[] _items) {
        Assert.notNull(_items, "Items is null");
        this.items = _items;
    }

    public class Item {

        private long published;
        private String title;
        private String id;
        private String[] categories;
        private Alternate[] alternate;
        private Summary summary;

        public long getPublished() {
            return this.published;
        }

        public void setPublished(final long _published) {
            this.published = _published;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(final String _title) {
            Assert.notNull(_title, "Title is null");
            this.title = _title;
        }

        public String getId() {
            return this.id;
        }

        public void setId(final String _id) {
            Assert.notNull(_id, "Id is null");
            this.id = _id;
        }

        public String[] getCategories() {
            return this.categories;
        }

        public void setCategories(final String[] _categories) {
            Assert.notNull(_categories, "Categories is null");
            this.categories = _categories;
        }

        public Alternate[] getAlternate() {
            return this.alternate;
        }

        public void setAlternate(final Alternate[] _alternate) {
            Assert.notNull(_alternate, "Alternate is null");
            this.alternate = _alternate;
        }

        public Summary getSummary() {
            return this.summary;
        }

        public void setSummary(final Summary _summary) {
            Assert.notNull(_summary, "Summary is null");
            this.summary = _summary;
        }

        public class Alternate {
            private String href;
            private String type;

            public String getHref() {
                return this.href;
            }

            public void setHref(final String _href) {
                Assert.notNull(_href, "HRef is null");
                this.href = _href;
            }

            public String getType() {
                return this.type;
            }

            public void setType(final String _type) {
                Assert.notNull(_type, "Type is null");
                this.type = _type;
            }

        }

        public class Summary {
            private String content;

            public String getContent() {
                return this.content;
            }

            public void setContent(final String content) {
                this.content = content;
            }
        }
    }

}
