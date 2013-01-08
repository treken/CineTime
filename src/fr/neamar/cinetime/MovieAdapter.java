package fr.neamar.cinetime;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.neamar.cinetime.objects.Movie;

public class MovieAdapter extends ArrayAdapter<Movie> {

	private Context context;
	
	/**
	 * Array list containing all the movies currently displayed
	 */
	public ArrayList<Movie> movies = new ArrayList<Movie>();

	public MovieAdapter(Context context, int textViewResourceId,
			ArrayList<Movie> movies) {
		super(context, textViewResourceId, movies);

		this.context = context;
		this.movies = movies;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if(v == null)
		{
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.listitem_movie, null);
		}
		
		Movie movie = movies.get(position);

		TextView movieTitle = (TextView) v.findViewById(R.id.listitem_movie_title);
		TextView movieDuration = (TextView) v.findViewById(R.id.listitem_movie_duration);
		TextView moviePressRating = (TextView) v.findViewById(R.id.listitem_movie_pressrating);
		TextView movieUserRating = (TextView) v.findViewById(R.id.listitem_movie_userrating);
		TextView movieDisplay = (TextView) v.findViewById(R.id.listitem_movie_display);

		movieTitle.setText(movie.title);
		
		movieDuration.setText(movie.getDuration());
		moviePressRating.setText(movie.pressRating);
		movieUserRating.setText(movie.userRating);
		movieDisplay.setText(movie.getDisplay());
		
		return v;
	}
}
