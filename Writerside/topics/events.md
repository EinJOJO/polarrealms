# Events

Events allow listening for specific realm actions.

## Available events

Polar Realms uses Redis Pub/Sub for network wide events and Guava for internal event management.

<deflist type="wide">
<def title="RealmLoadRequestEvent">
    <p>Fired when a realm is requested to be loaded on a specific host. The Host will try to load the realm upon receiving this event but might fail.</p>
</def>
<def title="RealmLoadDeniedEvent">
    <p>Dispatched when the host denies the loading of a realm. This can happen for various reasons, for example, if the host is already at maximum capacity.</p>
</def>
<def title="RealmLoadedEvent">
    <p>Dispatched when a realm has been loaded.</p>
</def>

<def title="RealmStateChangeEvent">
    <p>Dispatched when a realm state has changed.</p>
</def>
<def title="RealmUpdateEvent">
 <p>Called when the data of a realm changes.</p>
 <ul>
     <li>Adding a trusted member</li>
     <li>Removing a trusted member</li>
     <li>Changing the realm's alias</li>
     <li>Changing the realm's data</li>
     <li>...</li>
 </ul>
</def>
<def title="RealmPlayerEnterEvent">
    A player has entered a realm.
</def>
<def title="RealmPlayerLeaveEvent">
    A player has left a realm.
</def>
</deflist>