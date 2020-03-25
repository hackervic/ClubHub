package com.nucleus.events.clubhub.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nucleus.events.clubhub.ClubProfile;
import com.nucleus.events.clubhub.Model.ClubsModel;
import com.nucleus.events.clubhub.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Clubs_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Clubs_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// same problem n changeso
//ok
// yek bar app shuru se implement kr ke dekh lo//


public class Clubs_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    Clubs_fragment context = Clubs_fragment.this;
    FirebaseRecyclerAdapter<ClubsModel, ClubsViewHolder> firebaseRecyclerAdapter = null;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Clubs_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Clubs_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Clubs_fragment newInstance(String param1, String param2) {
        Clubs_fragment fragment = new Clubs_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clubs_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //   final List<ClubsModel> clubsM = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Clubs");


        Query query = databaseReference.orderByChild("approved");
        final FirebaseRecyclerOptions firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<ClubsModel>()
                .setQuery(query, ClubsModel.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ClubsModel, ClubsViewHolder>(firebaseRecyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull ClubsViewHolder holder, int position, @NonNull ClubsModel clubsModel) {

                final String str = firebaseRecyclerAdapter.getItem(position).getClubuid();
//                Toast.makeText(context, "Position ="+str, Toast.LENGTH_SHORT).show();

                holder.club_name.setText(clubsModel.getCname());
                holder.club_catagory.setText(clubsModel.getCcatagory());
                holder.club_collage.setText(clubsModel.getCcollage());
                holder.club_rating.setText(clubsModel.getCrating());
                holder.club_memeber.setText(clubsModel.getCmembers() + " Members");
                Glide.with(getActivity()).load(clubsModel.getClogo()).into(holder.c_image);


              holder.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
                      Intent i = new Intent(getContext(), ClubProfile.class);
                      i.putExtra("cuid",str);
                      startActivity(i);
                  }
              });

            }

            @NonNull
            @Override
            public ClubsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clubs, parent, false);

                ClubsViewHolder viewHolder = new ClubsViewHolder(view);

                return viewHolder;
            }
        };
        recyclerView = getActivity().findViewById(R.id.club_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();
        firebaseRecyclerAdapter.startListening();



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public static class ClubsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView club_name;
        public TextView club_catagory;
        public TextView club_collage;
        public TextView club_rating;
        public TextView club_memeber;
        public Button get_detail;
        ImageView c_image;

        public ClubsViewHolder(@NonNull View itemView) {
            super(itemView);
            //context = itemView.getContext();

            club_name = itemView.findViewById(R.id.c_name);
            club_catagory = itemView.findViewById(R.id.c_catagory);
            club_collage = itemView.findViewById(R.id.c_collage);
            c_image = itemView.findViewById(R.id.c_image);
            club_rating = itemView.findViewById(R.id.c_rating);
            club_memeber = itemView.findViewById(R.id.c_members);
            get_detail = itemView.findViewById(R.id.btn_get_details);


        }


        @Override
        public void onClick(View view) {

        }
    }
}
