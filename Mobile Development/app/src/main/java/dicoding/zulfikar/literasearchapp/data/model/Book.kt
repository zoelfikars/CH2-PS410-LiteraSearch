package dicoding.zulfikar.literasearchapp.data.model

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.LibraryBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.PopularBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.SearchedBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.TrendingBookEntity
import dicoding.zulfikar.literasearchapp.data.remote.response.BookResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(

    @field:SerializedName("coverUrl")
    val coverUrl: String,

    @field:SerializedName("publication_year")
    val publicationYear: Int,

    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("subject")
    val subject: List<String>? = null,

    @field:SerializedName("isbn")
    val isbn: String,

    @field:SerializedName("publisher")
    val publisher: String,

    @field:SerializedName("description")
    val description: List<String>? = null,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("id_perpus")
    val idPerpus: Int
//    val idPerpus: Int
) : Parcelable {
    fun toPopularBookEntity(): PopularBookEntity {
        return PopularBookEntity(
            coverUrl = coverUrl,
            publicationYear = publicationYear,
            author = author,
            subject = subject.toString(),
            isbn = isbn,
            publisher = publisher,
            description = description.toString(),
            title = title,
            idPerpus = idPerpus
        )
    }

    fun toTrendingBookEntity(): TrendingBookEntity {
        return TrendingBookEntity(
            coverUrl = coverUrl,
            publicationYear = publicationYear,
            author = author,
            subject = subject.toString(),
            isbn = isbn,
            publisher = publisher,
            description = description.toString(),
            title = title,
            idPerpus = idPerpus
        )
    }

    fun toBookEntity(): BookEntity {
        return BookEntity(
            coverUrl = coverUrl,
            publicationYear = publicationYear,
            author = author,
            subject = subject.toString(),
            isbn = isbn,
            publisher = publisher,
            description = description.toString(),
            title = title,
            idPerpus = idPerpus
        )
    }

    fun toLibraryBookEntity(): LibraryBookEntity {
        return LibraryBookEntity(
            coverUrl = coverUrl ?: "belum ada link cover",
            publicationYear = publicationYear,
            author = author,
            subject = subject.toString(),
            isbn = isbn,
            publisher = publisher,
            description = description.toString(),
            title = title,
            idPerpus = idPerpus
        )
    }

    fun toSearchBookEntity(): SearchedBookEntity {
        return SearchedBookEntity(
            coverUrl = coverUrl ?: "belum ada link cover",
            publicationYear = publicationYear,
            author = author,
            subject = subject.toString(),
            isbn = isbn,
            publisher = publisher,
            description = description.toString(),
            title = title,
            idPerpus = idPerpus
        )
    }
}

val jsonString =
    "{ \"status\": \"success\", \"data\": [ { \"title\": \"Classical Mythology\", \"author\": \"Mark P. O. Morford\", \"isbn\": \"0195153448\", \"publication_year\": 2002, \"publisher\": \"Oxford University Press\", \"id_perpus\": 913, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0195153448-S.jpg\", \"subject\": [ \"Classical Mythology\", \"Mythology, Classical\", \"Mythologie\", \"Klassieke oudheid\", \"Mythology\", \"Classical mythology\", \"Mythology, classical\" ], \"description\": [ \"An excellent primer on classical mythology for readers who have little or no background of classical knowledge.\" ] }, { \"title\": \"Prayers That Avail Much for Students\", \"author\": \"Germaine Copeland\", \"isbn\": \"1577941195\", \"publication_year\": 1998, \"publisher\": \"Harrison House\", \"id_perpus\": 173, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/1577941195-S.jpg\", \"subject\": [ null ], \"description\": [ null ] }, { \"title\": \"Omega Blue\", \"author\": \"Mel Odom\", \"isbn\": \"0061006165\", \"publication_year\": 1993, \"publisher\": \"Harper Mass Market Paperbacks (Mm)\", \"id_perpus\": 213, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0061006165-S.jpg\", \"subject\": [ \"Fiction, general\" ], \"description\": [ null ] }, { \"title\": \"Feng Shui: Harmony by Design\", \"author\": \"Nancy Santopietro\", \"isbn\": \"0399522395\", \"publication_year\": 1996, \"publisher\": \"Perigee Books\", \"id_perpus\": 173, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0399522395-S.jpg\", \"subject\": [ \"Feng shui\" ], \"description\": [ null ] }, { \"title\": \"A Man's Game\", \"author\": \"Newton Thornburg\", \"isbn\": \"0812553748\", \"publication_year\": 1997, \"publisher\": \"Tor Books (Mm)\", \"id_perpus\": 913, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0812553748-S.jpg\", \"subject\": [ \"Fiction\", \"Fathers and daughters\", \"Criminals\", \"Teenage girls\", \"Stalking\" ], \"description\": [ null ] }, { \"title\": \"Wind in the Willows (Signet Classics (Paperback))\", \"author\": \"Kenneth Grahame\", \"isbn\": \"0451524624\", \"publication_year\": 1990, \"publisher\": \"Signet Classics\", \"id_perpus\": 213, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0451524624-S.jpg\", \"subject\": [ \"Fiction\", \"Animals\", \"Friendship\", \"Juvenile fiction\", \"Toy and movable books\", \"Country life\", \"River life\", \"Specimens\", \"Christmas\", \"Folklore\", \"Pictorial works\", \"Children's stories, English\", \"Stories\", \"Juvenile literature\", \"Toad of Toad Hall (Fictitious character)\", \"Legends and stories of Animals\", \"Fairy tales\", \"Mythical Animals\", \"Fantastic fiction\", \"Forests and forestry\", \"Adventure and adventurers\", \"Children: Kindergarten\", \"Children: Grades 3-4\", \"Large type books\", \"Children's fiction\", \"Animals, fiction\", \"Friendship, fiction\", \"England, fiction\", \"Comics & graphic novels, general\", \"Fantasy\", \"Cartoons and comics\", \"Voyages and travels\", \"Home\", \"Hospitality\", \"Children's stories\", \"Toads\", \"Moles\", \"Rats\", \"Badgers\", \"Classical literature\", \"Fantasy fiction\", \"Toad of Toad Hall (Fictional character)\", \"Winds, fiction\", \"Country life, fiction\", \"Moles (Animals)\", \"Rivers\", \"Landscapes\", \"Disguise\", \"Ethics\", \"Obsessive-compulsive disorder\", \"Self-destructive behavior\", \"Otters\", \"Escapes\", \"Thieves\", \"Weasels\", \"Dwellings\", \"Lost articles\", \"Amitié\", \"Romans, nouvelles, etc. pour la jeunesse\", \"Crapauds\", \"Taupes\", \"Blaireaux\", \"Voyages\", \"Paysages\", \"Déguisement\", \"Morale\", \"Névroses obsessionnelles\", \"Comportement autodestructeur\", \"Loutres\", \"Évasions\", \"Voleurs\", \"Belettes\", \"Habitations\", \"Objets perdus\", \"Moles (animals), fiction\", \"Rats, fiction\", \"Adventure and adventurers, fiction\", \"Rivers, fiction\", \"Humorous stories\", \"Forests and forestry, fiction\", \"Literature and fiction, juvenile\", \"Animals -- Juvenile fiction\", \"Friendship -- Juvenile fiction\" ], \"description\": [ \"The adventures of four amiable animals, Rat, Toad, Mole and Badger, along a river in the English countryside.\" ] }, { \"title\": \"Nebenan.\", \"author\": \"Bernhard Hennen\", \"isbn\": \"3492236782\", \"publication_year\": 2002, \"publisher\": \"Piper\", \"id_perpus\": 173, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/3492236782-S.jpg\", \"subject\": [ null ], \"description\": [ null ] }, { \"title\": \"Who Am I, God?\", \"author\": \"M. Holmes\", \"isbn\": \"0553229885\", \"publication_year\": 1981, \"publisher\": \"Bantam Books (Mm)\", \"id_perpus\": 913, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0553229885-S.jpg\", \"subject\": [ \"Women\", \"Prayer-books and devotions\", \"English\", \"Prayers and devotions\", \"Large type books\", \"Christian life\", \"Women, prayers and devotions\" ], \"description\": [ null ] }, { \"title\": \"The Devil's Alternative\", \"author\": \"Frederick Forsyth\", \"isbn\": \"0670270814\", \"publication_year\": 1980, \"publisher\": \"Penguin USA\", \"id_perpus\": 173, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0670270814-S.jpg\", \"subject\": [ \"Fiction\", \"Political crimes and offenses\", \"Famines\", \"Nationalists\", \"Foreign relations\", \"Espionage\", \"Fiction, general\", \"Fiction, mystery & detective, general\" ], \"description\": [ \"A thriller, from the author of THE DAY OF THE JACKAL and THE DOGS OF WAR, in which the President of the USA and other statesmen throughout the world face a decision that will cost the lives of many people.\" ] }, { \"title\": \"The Judas Seed\", \"author\": \"Anthony Gentile\", \"isbn\": \"0440143756\", \"publication_year\": 1982, \"publisher\": \"Dell Publishing Company\", \"id_perpus\": 213, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0440143756-S.jpg\", \"subject\": [ null ], \"description\": [ null ] }, { \"title\": \"Tesaurus Melayu moden Utusan\", \"author\": \"Abdullah Hassan\", \"isbn\": \"9676104825\", \"publication_year\": 1994, \"publisher\": \"Utusan Publications &amp; Distributors\", \"id_perpus\": 173, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/9676104825-S.jpg\", \"subject\": [ \"Malay language\", \"Dictionaries\", \"Synonyms and antonyms\" ], \"description\": [ \"Thesaurus of modern Malay.\" ] }, { \"title\": \"Norman Plays Ice Hockey\", \"author\": \"Clare Gault\", \"isbn\": \"0590101447\", \"publication_year\": 1985, \"publisher\": \"Scholastic\", \"id_perpus\": 913, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0590101447-S.jpg\", \"subject\": [ \"Children's fiction\", \"Hockey, fiction\" ], \"description\": [ null ] }, { \"title\": \"The White Tiger\", \"author\": \"Robert Nathan\", \"isbn\": \"0446352063\", \"publication_year\": 1988, \"publisher\": \"Warner Books\", \"id_perpus\": 213, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0446352063-S.jpg\", \"subject\": [ \"Political corruption\", \"Police\", \"Fiction\", \"Fiction, general\" ], \"description\": [ null ] }, { \"title\": \"The Berenstain Bears Meet Questron: Left, Right, Stop, Go and Other Things You Need to Know (Questron Electronics Books)\", \"author\": \"Stan Berenstain\", \"isbn\": \"0394890566\", \"publication_year\": 1987, \"publisher\": \"Random House (Merchandising)\", \"id_perpus\": 173, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0394890566-S.jpg\", \"subject\": [ null ], \"description\": [ null ] }, { \"title\": \"Wife Worth Waiting For  (Everyday Miracles) (Love Inspired (Numbered))\", \"author\": \"Arlene James\", \"isbn\": \"0373870140\", \"publication_year\": 1997, \"publisher\": \"Steeple Hill\", \"id_perpus\": 213, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0373870140-S.jpg\", \"subject\": [ \"Fiction, general\" ], \"description\": [ null ] }, { \"title\": \"Temptations\", \"author\": \"Jessica March\", \"isbn\": \"0446352268\", \"publication_year\": 1989, \"publisher\": \"Warner Books\", \"id_perpus\": 173, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0446352268-S.jpg\", \"subject\": [ \"Fiction, general\" ], \"description\": [ null ] }, { \"title\": \"Clara Callan\", \"author\": \"Richard Bruce Wright\", \"isbn\": \"0002005018\", \"publication_year\": 2001, \"publisher\": \"HarperFlamingo Canada\", \"id_perpus\": 213, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0002005018-S.jpg\", \"subject\": [ \"Actresses\", \"Fiction\", \"Literature\", \"Sisters\", \"Women teachers\", \"Young women\", \"Diary fiction\", \"New york (n.y.), fiction\", \"Teachers, fiction\", \"Fiction, historical, general\", \"Ontario, fiction\", \"Actresses, fiction\", \"Sisters, fiction\", \"Young women, fiction\", \"Fiction, family life\", \"Fiction, psychological\" ], \"description\": [ \"E-book extras: \\\"Hero of the Humdrum\\\": A profile of Richard B. Wright by John Bemrose; prize citations.It is 1934, and in a small town in Canada, Clara Callan reluctantly takes leave of her sister, Nora, who is bound for the show business world of New York. Richard B. Wright's acclaimed novel, winner in 2001 of Canada's two most prestigious literary awards, is a mesmerizing tribute to friendship and sisterhood, romance and redemption.Winner in 2001 of Canada's two most prestigious literary awards -- the Governor General's Award and the Giller Prize -- Richard B. Wright's celebrated novel Clara Callan is the powerful, moving story of two sisters and their life-changing experiences on the eve of World War II.\" ] }, { \"title\": \"Home A Heart A Husband (Love Inspired : Heartwarming Inspirational Romance)\", \"author\": \"Lois Richer\", \"isbn\": \"0373870507\", \"publication_year\": 1998, \"publisher\": \"Steeple Hill\", \"id_perpus\": 913, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0373870507-S.jpg\", \"subject\": [ \"Fiction, romance, contemporary\", \"Fiction, religious\" ], \"description\": [ null ] }, { \"title\": \"Martha Speaks\", \"author\": \"Susan Meddaugh\", \"isbn\": \"0395633133\", \"publication_year\": 1992, \"publisher\": \"Houghton Mifflin/Walter Lorraine Books\", \"id_perpus\": 213, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/0395633133-S.jpg\", \"subject\": [ \"Dogs\", \"Fiction\", \"Spanish language materials\", \"Ficción juvenil\", \"Perros\", \"Soups\", \"Juvenile fiction\", \"Dogs, fiction\", \"Children's fiction\", \"Novela\", \"Materiales en español\", \"Spanish language\", \"Novela juvenil\" ], \"description\": [ \"Problems arise when Martha, the family dog, learns to speak after eating alphabet soup.\" ] }, { \"title\": \"Venezianisches BegrÃ?Â¤bnis.\", \"author\": \"Timothy Holme\", \"isbn\": \"3770145909\", \"publication_year\": 1998, \"publisher\": \"DUMONT Literatur und Kunst Verlag\", \"id_perpus\": 173, \"coverUrl\": \"http://covers.openlibrary.org/b/isbn/3770145909-S.jpg\", \"subject\": [ null ], \"description\": [ null ] } ] }"


val gson = Gson()
val bookResponse = gson.fromJson(jsonString, BookResponse::class.java)


val books: List<Book> = bookResponse.data


