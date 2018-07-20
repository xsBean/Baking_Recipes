package app.advanced.mdo.baking.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.List;

import app.advanced.mdo.baking.Models.bakingStep;
import app.advanced.mdo.baking.R;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link StepFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link StepFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class StepFragment extends Fragment implements Player.EventListener {


    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView myPlayerView;
    private PlaybackStateCompat.Builder  myStateBuilder;
    private MediaSessionCompat mMediaSession;
    private Uri videoUri;
    private int currentStep;
//    private Container container;


    public StepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StepDetailActivity activity = (StepDetailActivity) getActivity();
        assert activity != null;
        videoUri = activity.getVideoUri();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_details, container, false);

//        if (view instanceof RecyclerView) {
            Context context = view.getContext();
//            this.container = view.findViewById(R.id.player_container);
////            this.container.setLayoutManager(new LinearLayoutManager(context));
////            RecyclerViewStepDetailAdapter stepDetailAdapter = new RecyclerViewStepDetailAdapter(bakingStepList,currentStep,context);
////            this.container.setAdapter(stepDetailAdapter);
////            RecyclerView recyclerView = view.findViewById(R.id.rv_step_details);
////            recyclerView.setLayoutManager(new LinearLayoutManager(context));
////            RecyclerViewStepDetailAdapter adapter = new RecyclerViewStepDetailAdapter(bakingStepList,currentStep,context );
////            recyclerView.setAdapter(adapter);
////            recyclerView.scrollToPosition(currentStep);
//        }

        myPlayerView = view.findViewById(R.id.exoplayer_view);
        TextView tvDescription = view.findViewById(R.id.tv_long_description);
        Button btNextStep = view.findViewById(R.id.bt_next_step);

            // Start exoplayer
//            myPlayerView.setVisibility(View.VISIBLE);
        initializeMediaSession(context);
        initializePlayer(videoUri, context);


//        // Set text and button
//        tvDescription.setText(step.getDescription());
//        if(currentStep == bakingStepList.size()){
//            btNextStep.setVisibility(View.GONE);
//        }else {
//            btNextStep.setText(bakingStepList.get(currentStep+1).getShortDescription());
//            btNextStep.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnStepFragmentInteractionListener) {
//            mListener = (OnStepFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
        mMediaSession.setActive(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }

    /**
     * Release ExoPlayer
     */

    private void releasePlayer() {
        if(exoPlayer != null){
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
    private void initializeMediaSession(Context context) {
        //Creating media session
        mMediaSession = new MediaSessionCompat(context, "Media Session - TAG");
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);

        myStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_SKIP_TO_NEXT);
        mMediaSession.setCallback(new mySessionCallback());
        mMediaSession.setActive(true);
    }
    private void initializePlayer(Uri uri, Context context) {

        // Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        //Initialize the player
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        // Initialize simpleExoPlayerView
        myPlayerView.setPlayer( exoPlayer);

        MediaSource mediaSource = buildMediaSource(uri);
//        MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
//                : new ConcatenatingMediaSource(mediaSources);
        exoPlayer.prepare(mediaSource, true,false);
        exoPlayer.setPlayWhenReady(true);
    }

    private MediaSource buildMediaSource(Uri uri){
//        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("Test ExoPlayer");
        ExtractorMediaSource.Factory factory = new ExtractorMediaSource.Factory(dataSourceFactory);
        ExtractorMediaSource audioSource = factory.createMediaSource(uri);
//        int numberSteps = bakingStepList.size();
//        MediaSource mediaSources = new MediaSource[numberSteps];

//        for(int i = 0; i< numberSteps; i++){
//            Uri uri = Uri.parse(bakingStepList.get(i).getVideoURL());
//            if (uri == null) continue;
//            ExtractorMediaSource.Factory factory = new ExtractorMediaSource.Factory(dataSourceFactory);
//            ExtractorMediaSource audioSource = factory.createMediaSource(uri);
//            mediaSources[i] = audioSource;
//        }
        return audioSource;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if(playbackState == Player.STATE_READY && playWhenReady){
            // we are playing
            myStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, exoPlayer.getCurrentPosition(),1f);
        }else if(playbackState == Player.STATE_READY){
            // we are paused
            myStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, exoPlayer.getCurrentPosition(),1f);
        }
        mMediaSession.setPlaybackState(myStateBuilder.build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

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
    private class mySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            exoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer.seekTo(0);
        }
    }
}
