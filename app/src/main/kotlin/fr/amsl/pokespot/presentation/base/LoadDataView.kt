package fr.amsl.pokespot.presentation.base

/**
 * Interface representing a View that will need to load data.
 */
interface LoadDataView : View {
  /**
   * Show a view with a progress bar indicating a loading process.
   */
  fun showLoadingView()

  /**
   * Hide a loading view.
   */
  fun hideLoadingView()

  /**
   * Hide a loading view.
   */
  fun showRetry()

  /**
   * Hide a retry view shown if there was an error when retrieving data.
   */
  fun hideRetry()

  /**
   * Show an error message.
   *
   * @param message A string representing an error.
   */
  fun showError(message: String)
}