package com.example.keenan.moviespeedpicker;

import android.content.Context;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.keenan.moviespeedpicker.Proxy.ListRequest;
import com.example.keenan.moviespeedpicker.Proxy.ListResponse;
import com.example.keenan.moviespeedpicker.Proxy.ProxyService;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private Button startSearchButton;
    private Spinner genreSelect;
    private Spinner ratingSelect;
    private Spinner lengthSelect;

    private ArrayAdapter<CharSequence> gAdapter;
    private ArrayAdapter<CharSequence> rAdapter;
    private ArrayAdapter<CharSequence> lAdapter;

    private String selectedGenre = null;
    private String selectedRating = null;
    private String selectedLength = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.genres, android.R.layout.simple_spinner_item);

        rAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.ratings, android.R.layout.simple_spinner_item);

        lAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.lengths, android.R.layout.simple_spinner_item);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        startListen(view);
        return view;
    }

    public void startListen(View view){
        loadSpinners(view);
        startSearchButton = (Button) view.findViewById(R.id.button_search);
        startSearchButton.setEnabled(true);
        startSearchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String genre = genreSelect.getSelectedItem().toString();
                String rating = ratingSelect.getSelectedItem().toString();
                String length = lengthSelect.getSelectedItem().toString();

                new UserAsync().execute(genre, rating, length);
            }
        });

    }

    public void loadSpinners(View view){
        genreSelect = (Spinner) view.findViewById(R.id.genre_spinner);
        ratingSelect = (Spinner) view.findViewById(R.id.rating_spinner);
        lengthSelect = (Spinner) view.findViewById(R.id.length_spinner);

        genreSelect.setAdapter(gAdapter);
        ratingSelect.setAdapter(rAdapter);
        lengthSelect.setAdapter(lAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class UserAsync extends AsyncTask<String, Void, String> {
        private String url;

        @Override
        protected String doInBackground(String... params) {
            //url = "http://10.0.2.2:8081/";
            url = "http://192.168.1.107:8081/";


                ListRequest list = new ListRequest();
                list.setGenre(params[0]);
                list.setRating(params[1]);
                int minutes = timeConversion(params[2]);
                list.setRuntime(minutes);

                MovieResults.getInstance().setSelectedMovieRating(params[1]);

                ProxyService proxy = new ProxyService();
                try {
                    ListResponse response = proxy.getList(list, url);
                    MovieResults.getInstance().setMovies(response.getResults());
                    return "success!";
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            startActivity(ListActivity.class);
        }
    }
    void startActivity(Class aClass) {
        Intent intent = new Intent(getContext(), aClass);
        startActivity(intent);
    }
    private int timeConversion(String min){
        Double minutes = Double.parseDouble(min);
        minutes = (minutes * 60);
        int result = minutes.intValue();
        return result;
    }
}
