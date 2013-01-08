package fr.neamar.cinetime;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import fr.neamar.cinetime.api.APIHelper;
import fr.neamar.cinetime.objects.Movie;

public class MoviesActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movies);

		new LoadMoviesTask().execute(getIntent().getStringExtra("code"));
	}

	private class LoadMoviesTask extends
			AsyncTask<String, Void, ArrayList<Movie>> {
		private final ProgressDialog dialog = new ProgressDialog(
				MoviesActivity.this);

		@Override
		protected void onPreExecute() {
			this.dialog.setMessage("Chargement des séances en cours...");
			this.dialog.show();

		}

		@Override
		protected ArrayList<Movie> doInBackground(String... queries) {
			ArrayList<Movie> resultsList = new ArrayList<Movie>();

			JSONArray jsonResults = APIHelper.findMovies(queries[0]);

			for (int i = 0; i < jsonResults.length(); i++) {
				JSONObject jsonMovie, jsonShow;
				try {
					jsonMovie = jsonResults.getJSONObject(i);
					jsonShow = jsonMovie.getJSONObject("onShow").getJSONObject("movie");

					Movie movie = new Movie();
					movie.title = jsonShow.getString("title");
					movie.duration = jsonShow.getInt("runtime");
					movie.duration = jsonShow.getInt("runtime");
					movie.pressRating = jsonShow.getJSONObject("statistics").getString("pressRating");
					movie.userRating = jsonShow.getJSONObject("statistics").getString("userRating");
					movie.display = jsonMovie.getString("display");

					resultsList.add(movie);

				} catch (JSONException e) {
					Log.e("wtf",e.getMessage());
					e.printStackTrace();
				}
			}

			return resultsList;

		}

		@Override
		protected void onPostExecute(ArrayList<Movie> resultsList) {
			if (this.dialog.isShowing())
				this.dialog.dismiss();
			setListAdapter(new MovieAdapter(MoviesActivity.this,
					R.layout.listitem_theater, resultsList));
		}
	}
}