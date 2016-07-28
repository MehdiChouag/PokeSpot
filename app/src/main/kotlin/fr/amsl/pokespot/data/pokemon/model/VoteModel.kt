package fr.amsl.pokespot.data.pokemon.model

import android.content.ContentValues

/**
 * @author mehdichouag on 27/07/2016.
 */
data class VoteModel(val isUpVoted: Boolean, val isDownVoted: Boolean) {
  companion object {
    val TABLE_VOTE = "vote"

    val ID = "_id"
    val UNIQUE_ID = "unique_id"
    val DOWNVOTE = "downvote"
    val UPVOTE = "upvote"

    val SELECT_BY_UNIQUE_ID = "SELECT * FROM $TABLE_VOTE WHERE $UNIQUE_ID=?"
  }

  class Builder {
    private val contentValue = ContentValues()

    fun uniqueId(uniqueId: String): Builder {
      contentValue.put(UNIQUE_ID, uniqueId)
      return this
    }

    fun downvote(isDownVoted: Boolean): Builder {
      contentValue.put(DOWNVOTE, if (isDownVoted) 1 else 0)
      return this
    }

    fun upvote(isDownVoted: Boolean): Builder {
      contentValue.put(UPVOTE, if (isDownVoted) 1 else 0)
      return this
    }

    fun build(): ContentValues = contentValue
  }
}